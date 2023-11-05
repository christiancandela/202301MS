package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Implementación del API de Tokens {@link TokenAPI} para la gestión de los tokens. Permite registrarlos y obtenerlos.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */

@Path("/tokens")
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
@Singleton
public class TokenController implements TokenAPI {
    private static final Logger LOGGER = Logger.getLogger(TokenController.class.getName());

    private final TokenService service;

    @Inject
    public TokenController(TokenService service) {
        this.service = service;
    }

    /**
     * @see TokenAPI#create(Credential)
     */
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

    /**
     * @see TokenAPI#check(String)
     */
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

    /**
     * @see TokenAPI#list()
     */
    @Override
    @GET
    @RolesAllowed({"admin"})
    public Collection<Token> list(){
        LOGGER.info("Operacion list");
        return service.get();
    }
}
