package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.util.Message;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.identitystore.PasswordHash;
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
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
@Singleton
public class UsuarioController {

    private static final Logger LOGGER = Logger.getLogger(UsuarioController.class.getName());
    @Inject
    private UsuarioRepository repository;


    @Inject
    private JsonWebToken principal;

    @Inject
    private PasswordHash passwordHash;

    @POST
    public Response create( @Valid Usuario usuario) {
        LOGGER.info("Operacion registrar usuario");
        var rols = Optional.ofNullable( usuario.rols() ).orElse(Set.of("user"));
        var nuevoUsuario = repository.save(
                Usuario.builder().id(usuario.id()).username(usuario.username())
                        .password(passwordHash.generate(usuario.password().toCharArray()))
                        .rols(rols).build());
        URI uri = UriBuilder.fromPath("/{id}").build(nuevoUsuario.id());
        return Response.created(uri)
                .entity(nuevoUsuario).build() ;
    }

    @DELETE
    @Path("{username}")
    @RolesAllowed({"user"})
    public Response delete(@PathParam("username") String username){
//        public Response delete(@PathParam("id") String id,@HeaderParam("Authorization") String authorization){
        LOGGER.info("Operacion delete user");
        Objects.requireNonNull(username,"El id del usuario no puede ser nulo");

        if( principal == null ){
            LOGGER.warning("Usuario no autorizado para realizar la operación.");
            throw new WebApplicationException("Usuario no autorizado para realizar la operación.", Response.Status.UNAUTHORIZED);
        }
        if(!principal.getName().equalsIgnoreCase(username)){
            LOGGER.warning("Usuario no posee permisos para realizar la operación.");
            throw new WebApplicationException("Usuario no posee permisos para realizar la operación.", Response.Status.FORBIDDEN);
        }
        var usuario = getAndVerify(username);

        repository.deleteById(usuario.id());
        return Response.noContent().entity(Message.of("Operación exitosa")).build();
    }

    @GET
    @Path("{username}")
    @RolesAllowed({"user"})
    public Response get(@PathParam("username") String username){
        LOGGER.info("Operacion get usuario");
        Objects.requireNonNull(username,"El nombre de usuario no puede ser nulo");
        return Response.ok(getAndVerify(username)).build();
    }

    @GET
    @RolesAllowed({"user"})
    public Collection<Usuario> list(@QueryParam("usuario") String username,@QueryParam("rol") String rol,@QueryParam("order") String order){
        LOGGER.info("Operacion list");
        return repository.find(UsuarioUtil.INSTANCE.find(username,rol), UsuarioUtil.INSTANCE.order(order));
    }

    private Usuario getAndVerify(String username){
        Optional<Usuario> usuario = repository.findByUsername(username);
        return usuario.orElseThrow(()->new WebApplicationException("Usuario no encontrado.", Response.Status.NOT_FOUND));
    }
}
