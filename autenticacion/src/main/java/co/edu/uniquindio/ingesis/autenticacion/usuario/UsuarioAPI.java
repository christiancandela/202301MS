package co.edu.uniquindio.ingesis.autenticacion.usuario;

import jakarta.annotation.Nonnull;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.Collection;

public interface UsuarioAPI {
    @Operation(
            summary = "Registrar usuario",
            description = "Permite registrar un usuario en el sistema"
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "201",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Usuario.class))
            ),
            @APIResponse(responseCode = "400",description = "La solicitud está incompleta",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,oneOf = Error.class))
            )
    })
    Response create(@Valid Usuario usuario);

    @RolesAllowed({"user", "admin"})
    Response delete(@PathParam("username") @Nonnull String username);

    @RolesAllowed({"user", "admin"})
    Response get(@PathParam("username") String username);

    @RolesAllowed({"admin"})
    Collection<Usuario> list(@QueryParam("usuario") String username, @QueryParam("rol") String rol, @QueryParam("order") String order);
}
