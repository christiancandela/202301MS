package co.edu.uniquindio.ingesis.autenticacion.token;

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

/**
 *
 */
@Path("/tokens")
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
@Singleton
public class TokenController {

    @Inject
    private TokenRepository repository;

    @POST
    public Response create( @Valid Credential credential) {
        if( !credential.getUserName().equals(credential.getPassword()) ){
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
        Objects.requireNonNull(token,"El token no puede ser nulo");
        throw new WebApplicationException("Operación en construcción", Response.Status.INTERNAL_SERVER_ERROR);
    }

    @GET
    @Path("{token}")
    public Response check(@PathParam("token") String token){
        Objects.requireNonNull(token,"El token no puede ser nulo");
        throw new WebApplicationException("Token no encontrado.", Response.Status.NOT_FOUND);
    }

    @GET
    public Collection<Token> list(){
        return repository.getAll();
    }
}
