package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.util.Message;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.SecurityContext;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 */
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
@Singleton
public class UsuarioController implements UsuarioAPI {

    private static final Logger LOGGER = Logger.getLogger(UsuarioController.class.getName());
    @Inject
    private Principal principal;

    @Inject
    private SecurityContext securityContext;
    @Inject
    private UsuarioService service;

    @Override
    @POST
    public Response create(@Valid Usuario usuario) {
        LOGGER.info("Operacion registrar usuario");
        if( usuario == null ){
            throw new WebApplicationException("El usuario es requerido", Response.Status.BAD_REQUEST);
        }
        var nuevoUsuario = service.register(usuario);
        if( nuevoUsuario.isEmpty() ){
            throw new WebApplicationException("No se pudo registrar el usuario", Response.Status.INTERNAL_SERVER_ERROR);
        }
        URI uri = UriBuilder.fromPath("/{id}").build(nuevoUsuario.get().id());
        return Response.created(uri)
                .entity(nuevoUsuario.get()).build() ;
    }

    @Override
    @DELETE
    @Path("{username}")
    @RolesAllowed({"user","admin"})
    public Response delete(@PathParam("username") @Nonnull String username){
        LOGGER.info("Operacion delete user");
        if( securityContext.isCallerInRole("admin") ){
            service.unregister(username);
        } else {
            service.unregister(username, principal.getName());
        }
        return Response.noContent().entity(Message.of("Operación exitosa")).build();
    }

    @Override
    @GET
    @Path("{username}")
    @RolesAllowed({"user","admin"})
    public Response get(@PathParam("username") String username){
        LOGGER.info("Operacion get usuario");
        Objects.requireNonNull(username,"El nombre de usuario no puede ser nulo");
        return Response.ok(service.get(username)).build();
    }

    @Override
    @GET
    @RolesAllowed({"admin"})
    public Collection<Usuario> list(@QueryParam("usuario") String username, @QueryParam("rol") String rol, @QueryParam("order") String order){
        LOGGER.info("Operacion list");
        return service.get(username, rol, order);
    }
}
