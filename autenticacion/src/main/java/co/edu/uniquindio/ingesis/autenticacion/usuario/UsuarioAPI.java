package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.util.Message;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Encoding;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

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
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "409",description = "El usuario ya existe",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            )
    })
    Response create(@RequestBody(description = "Usuario a registrar", required = true,
            content = @Content(schema = @Schema(implementation = Usuario.class))) @Valid Usuario usuario);

    @Operation(
            summary = "Dar de baja un usuario",
            description = "Permite dar de baja (borrar) un usuario en el sistema"

    )
    @APIResponses(value = {
            @APIResponse(responseCode = "203",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Message.class))
            ),
            @APIResponse(responseCode = "401",description = "Usuario no autorizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "403",description = "No tiene permitido realizar la operación",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "404",description = "Recurso no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            )
    })
    @SecurityRequirement(name = "JWT",scopes = {"user","admin"})
    Response delete(@Parameter(description = "nombre del usuario que se desea dar de baja", example = "pedro") @PathParam("username") @NotBlank String username);

    @Operation(
            summary = "obtener datos de usuario",
            description = "Permite obtener los datos de un usuario"

    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Usuario.class))
            ),
            @APIResponse(responseCode = "401",description = "Usuario no autorizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "403",description = "No tiene permitido realizar la operación",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "404",description = "Recurso no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            )
    })
    @SecurityRequirement(name = "JWT",scopes = {"user","admin"})
    Response get(@Parameter(description = "nombre del usuario del que se desea obtener los datos", example = "pedro") @NotBlank @PathParam("username") String username);

    @Operation(
            summary = "lista los usuarios",
            description = "Permite obtener un listado de los usuarios registrados en el sistema, es posible filtrarlos por rol y por el nombre de usuario, de igual forma es posible ordenarlos ASC o DESC"

    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Usuario.class))
            ),
            @APIResponse(responseCode = "401",description = "Usuario no autorizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "403",description = "No tiene permitido realizar la operación",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "404",description = "Recurso no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            )
    })
    @SecurityRequirement(name = "JWT",scopes = {"admin"})
    Collection<Usuario> list(@Parameter(name = "usuario", description = "nombre del usuario por el que se desea filtrar", example = "pedro") @QueryParam("usuario") String username,
                             @Parameter(description = "rol por el que se desea filtrar, si es especificado solo se mostrarán aquellos usuarios que posean dicho rol.", example = "admin")
                             @QueryParam("rol") String rol,
                             @Parameter(description = "El listado es ordenado por nombre de usuario, order permite indicar si se desea en orden ascendente (ASC) o descendente (DESC).", example = "DESC")
                             @QueryParam("order") String order);
}
