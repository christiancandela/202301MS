package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 *
 */
@Path("/tokens")
@Singleton
public class TokenController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Token> obtenerTokens() {
        return Collections.EMPTY_LIST;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create( Credential credential) {
        throw new WebApplicationException("Operación en construcción", Response.Status.INTERNAL_SERVER_ERROR);
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
        return Collections.EMPTY_LIST;
    }
}
