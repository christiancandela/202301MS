package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.util.Message;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;
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
    private UsuarioService service;

    /**
     * @see UsuarioAPI#create(Usuario) 
     */
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

    /**
     * @see UsuarioAPI#update(String, Usuario) 
     */
    @Override
    @PUT
    @Path("{username}")
    @RolesAllowed({"user","admin"})
    public Response update(@PathParam("username") @Nonnull String username,@Nonnull Usuario usuario) {
        LOGGER.info("Operacion actualizar user");
        Optional<Usuario> resultado= service.update(usuario);
        return Response
                .ok(resultado
                        .orElseThrow(
                                ()->new WebApplicationException("No se pudo actualizar el usuario", Response.Status.INTERNAL_SERVER_ERROR)
                        )
                )
                .build();
    }

    /**
     * @see UsuarioAPI#updatePassword(String, PasswordUpdateDTO) 
     */
    @Override
    @PATCH
    @Path("{username}")
    @RolesAllowed({"user","admin"})
    public Response updatePassword(@PathParam("username") @Nonnull String username, @Nonnull PasswordUpdateDTO passwordUpdateDTO) {
        LOGGER.info("Operacion actualizar password");
        Optional<Usuario> resultado = service.updatePassword(username,passwordUpdateDTO);
        return Response
                .ok(resultado
                        .orElseThrow(
                                ()->new WebApplicationException("No se pudo actualizar el usuario", Response.Status.INTERNAL_SERVER_ERROR)
                        )
                )
                .build();
    }

    /**
     * @see UsuarioAPI#delete(String)
     */
    @Override
    @DELETE
    @Path("{username}")
    @RolesAllowed({"user","admin"})
    public Response delete(@PathParam("username") @Nonnull String username){
        LOGGER.info("Operacion delete user");
        service.unregister(username);
        return Response.noContent().entity(Message.of("Operación exitosa")).build();
    }

    /**
     * @see UsuarioAPI#get(String) 
     */
    @Override
    @GET
    @Path("{username}")
    @RolesAllowed({"user","admin"})
    public Response get(@PathParam("username") @NotBlank String username){
        LOGGER.info("Operacion get usuario");
        Usuario resultado= service.get(username);
        return Response.ok(resultado).build();
    }

    /**
     * @see UsuarioAPI#list(String, String, String) 
     */
    @Override
    @GET
    @RolesAllowed({"admin"})
    public Collection<Usuario> list(@QueryParam("usuario") String username, @QueryParam("rol") String rol, @QueryParam("order") String order){
        LOGGER.info("Operacion list");
        return service.get(username, rol, order);
    }
}
