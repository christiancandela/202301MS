package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.util.Message;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 *
 */
@Path("/tokens")
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
@Singleton
public class TokenController {

    private static final Logger LOGGER = Logger.getLogger(TokenController.class.getName());
    @Inject
    private TokenRepository repository;

    @POST
    public Response create( @Valid Credential credential) {
        LOGGER.info("Operacion login");
        if( !credential.getUserName().equals(credential.getPassword()) ){
            LOGGER.warning("Nombre de usuario o clave incorrectas.");
            throw new WebApplicationException("Nombre de usuario o clave incorrectas.", Response.Status.UNAUTHORIZED);
        }
        Token token = repository.save(Token.of(credential.getUserName()));
        URI uri = UriBuilder.fromPath("/{id}").build(token.getToken());
        return Response.created(uri)
                .entity(token).header("Authorization","Bearer "+token.getToken()).build() ;
    }

    @DELETE
    @Path("{token}")
    public Response delete(@PathParam("token") String token,@HeaderParam("Authorization") String authorization){
        LOGGER.info("Operacion logout");
        Objects.requireNonNull(token,"El token no puede ser nulo");

        if( authorization == null || repository.findById(authorization.substring(7)).isEmpty() ){
            LOGGER.warning("Usuario no autorizado para realizar la operación.");
            throw new WebApplicationException("Usuario no autorizado para realizar la operación.", Response.Status.UNAUTHORIZED);
        }
        if(!authorization.substring(7).equals(token)){
            LOGGER.warning("Usuario no posee permisos para realizar la operación.");
            throw new WebApplicationException("Usuario no posee permisos para realizar la operación.", Response.Status.FORBIDDEN);
        }
        getAndVerify(token);
        repository.deleteById(token);
        return Response.noContent().entity(Message.of("Operación exitosa")).build();
    }

    @GET
    @Path("{token}")
    public Response check(@PathParam("token") String token){
        LOGGER.info("Operacion check");
        Objects.requireNonNull(token,"El token no puede ser nulo");
        return Response.ok(getAndVerify(token)).build();
    }

    @GET
    public Collection<Token> list(){
        LOGGER.info("Operacion list");
        return repository.getAll();
    }

    private Token getAndVerify(String id){
        Optional<Token> token = repository.findById(id);
        LOGGER.warning("Token no encontrado.");
        return token.orElseThrow(()->new WebApplicationException("Token no encontrado.", Response.Status.NOT_FOUND));
    }
}
