package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.seguridad.TokenUtilFactory;
import co.edu.uniquindio.ingesis.autenticacion.util.Message;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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


    @Inject
    private JsonWebToken principal;


    @POST
    public Response create( @Valid Credential credential) {
        LOGGER.info("Operacion login");
        if( !credential.getUserName().equals(credential.getPassword()) ){
            LOGGER.warning("Nombre de usuario o clave incorrectas.");
            throw new WebApplicationException("Nombre de usuario o clave incorrectas.", Response.Status.UNAUTHORIZED);
        }
        Token token = repository.save(TokenUtilFactory.getDefault().of().create(credential.getUserName(), Set.of("user")));
        TokenEvent.NEW.createEvent(token);
        URI uri = UriBuilder.fromPath("/{id}").build(token.token());
        return Response.created(uri)
                .entity(token).header("Authorization","Bearer "+token.token()).build() ;
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed({"user"})
    public Response delete(@PathParam("id") String id){
//        public Response delete(@PathParam("id") String id,@HeaderParam("Authorization") String authorization){
        LOGGER.info("Operacion logout");
        Objects.requireNonNull(id,"El id del token no puede ser nulo");

        if( principal == null ){
            LOGGER.warning("Usuario no autorizado para realizar la operación.");
            throw new WebApplicationException("Usuario no autorizado para realizar la operación.", Response.Status.UNAUTHORIZED);
        }
        if(!principal.getTokenID().equals(id)){
            LOGGER.warning("Usuario no posee permisos para realizar la operación.");
            throw new WebApplicationException("Usuario no posee permisos para realizar la operación.", Response.Status.FORBIDDEN);
        }
        var token = getAndVerify(id);
        repository.deleteById(id);
        TokenEvent.REMOVE.createEvent(token);
        return Response.noContent().entity(Message.of("Operación exitosa")).build();
    }

    @GET
    @Path("{id}")
    public Response check(@PathParam("id") String id){
        LOGGER.info("Operacion check");
        Objects.requireNonNull(id,"El token no puede ser nulo");
        return Response.ok(getAndVerify(id)).build();
    }

    @GET
    @RolesAllowed({"user"})
    public Collection<Token> list(){
        LOGGER.info("Operacion list");
        return repository.getAll();
    }

    private Token getAndVerify(String id){
        Optional<Token> token = repository.findById(id);
        return token.orElseThrow(()->new WebApplicationException("Token no encontrado.", Response.Status.NOT_FOUND));
    }
}
