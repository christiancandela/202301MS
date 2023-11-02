package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.util.Message;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.logging.Logger;

/**
 *
 */
@Path("/tokens")
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
@Singleton
public class TokenController implements TokenAPI {
    private static final Logger LOGGER = Logger.getLogger(TokenController.class.getName());

//    private JsonWebToken principal;
    private final Principal principal;

    private final TokenService service;

    @Inject
    public TokenController(Principal principal, TokenService service) {
        this.principal = principal;
        this.service = service;
    }

    @Override
    @POST
    public Response create(@Valid Credential credential) {
        LOGGER.info("Operacion login");
        var token = service.generate(credential);
        if(token.isEmpty()){
            LOGGER.warning("Nombre de usuario o clave incorrectas.");
            throw new WebApplicationException("Nombre de usuario o clave incorrectas.", Response.Status.UNAUTHORIZED);
        }
        URI uri = UriBuilder.fromPath("/{id}").build(token.get().id());
        return Response.created(uri)
                .entity(token).header("Authorization","Bearer "+token.get().token()).build() ;
    }

    @Override
    @DELETE
    @Path("{id}")
    @RolesAllowed({"user","admin"})
    public Response delete(@PathParam("id") String id){
        LOGGER.info("Operacion logout");
        if( id == null ){
            throw new WebApplicationException("El id es requerido", Response.Status.BAD_REQUEST);
        }
        service.invalidate(id,principal.getName());
        return Response.noContent().entity(Message.of("Operación exitosa")).build();
    }

    @Override
    @GET
    @Path("{id}")
    @RolesAllowed({"user","admin"})
    public Response check(@PathParam("id") String id){
        LOGGER.info("Operacion check");
        if( id == null ){
            throw new WebApplicationException("El id es requerido", Response.Status.BAD_REQUEST);
        }
        return Response.ok(service.get(id)).build();
    }

    @Override
    @GET
    @RolesAllowed({"admin"})
    public Collection<Token> list(){
        LOGGER.info("Operacion list");
        return service.get();
    }

}
