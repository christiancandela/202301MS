package co.edu.uniquindio.ingesis.autenticacion.tokens;

import javax.validation.Valid;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

/**
 *  URL/api/hello   GET 
 */
@Path("/tokens")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class TokensController {

    @POST
    public Response login(@Valid Usuario usuario){
        if( !usuario.getUsuario().equals(usuario.getClave()) ){
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(Collections.singleton(Error.of("Nombre de usuario o clave incorrectas.")))
                    .build();
        }
        Token token = new Token(usuario.getUsuario());
        return Response.status(Response.Status.CREATED)
                .entity(token)
                .header("Authorization","Bearer "+token.getToken())
                .build();
    }

    @DELETE
    public void logout(){

    }

    @GET
    public void check(){

    }
}
