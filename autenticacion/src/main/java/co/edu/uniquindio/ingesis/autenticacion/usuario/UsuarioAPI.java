package co.edu.uniquindio.ingesis.autenticacion.usuario;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.util.Collection;

public interface UsuarioAPI {
    @Operation(
            summary = "Registrar usuario",
            description = "Permite registrar un usuario en el sistema",operationId = "UsuarioAPI :: registrar"

    )
    @APIResponses(value = {
            @APIResponse(responseCode = "201",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Usuario"))
            ),
            @APIResponse(responseCode = "400",description = "La solicitud está incompleta",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"El usuario y la contraseña son requeridos\"}]")
            ),
            @APIResponse(responseCode = "409",description = "El usuario ya existe",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"El usuario ya existe\"}]")
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Ocurrió un error interno al procesar la solicitud\"}]")
            )
    })
    Response create(@RequestBody(description = "Usuario a registrar", required = true,
            content = @Content(schema = @Schema(ref = "Usuario"))) @Valid Usuario usuario);

    @Operation(
            summary = "Dar de baja un usuario",
            description = "Permite dar de baja (borrar) un usuario en el sistema",operationId = "UsuarioAPI :: unregister"
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "204",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Message"))
            ),
            @APIResponse(responseCode = "401",description = "Usuario no autorizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Usuario no autorizado\"}]")
            ),
            @APIResponse(responseCode = "403",description = "No tiene permitido realizar la operación",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"No tiene permitido realizar la operación\"}]")
            ),
            @APIResponse(responseCode = "404",description = "Recurso no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Recurso no encontrado\"}]")
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Ocurrió un error interno al procesar la solicitud\"}]")
            )
    })
    @SecurityRequirement(name = "JWT",scopes = {"user","admin"})
    Response delete(@Parameter(description = "nombre del usuario que se desea dar de baja", example = "pedro",required = true) @PathParam("username") @NotBlank String username);

    @Operation(
            summary = "Actualizar usuario",
            description = "Permite actualizar los datos de un usuario en el sistema",operationId = "UsuarioAPI :: actualizar"
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Usuario"))
            ),
            @APIResponse(responseCode = "400",description = "Datos incompletos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            ),
            @APIResponse(responseCode = "401",description = "Usuario no autorizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Usuario no autorizado\"}]")
            ),
            @APIResponse(responseCode = "403",description = "No tiene permitido realizar la operación",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"No tiene permitido realizar la operación\"}]")
            ),
            @APIResponse(responseCode = "404",description = "Recurso no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Recurso no encontrado\"}]")
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Ocurrió un error interno al procesar la solicitud\"}]")
            )
    })
    @SecurityRequirement(name = "JWT",scopes = {"user","admin"})
    Response update(@Parameter(description = "nombre del usuario que se desea actualizar", example = "pedro",required = true)
                    @PathParam("username") @NotBlank String username,
                    @RequestBody(description = "Datos del usuario a actualizar", required = true,
                        content = @Content(schema = @Schema(ref = "Usuario")))
                    @Nonnull @Valid Usuario usuario);

    @Operation(
            summary = "Actualizar clave",
            description = "Permite actualizar la clave de un usuario en el sistema",operationId = "UsuarioAPI :: actualizar clave"
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Usuario"))
            ),
            @APIResponse(responseCode = "400",description = "La solicitud está incompleta, el clave actual, la nueva clave y la verificación de la nueva clave son requeridas",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            ),
            @APIResponse(responseCode = "401",description = "Usuario no autorizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Usuario no autorizado\"}]")
            ),
            @APIResponse(responseCode = "403",description = "No tiene permitido realizar la operación",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"No tiene permitido realizar la operación\"}]")
            ),
            @APIResponse(responseCode = "404",description = "Recurso no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Recurso no encontrado\"}]")
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Ocurrió un error interno al procesar la solicitud\"}]")
            )
    })
    @SecurityRequirement(name = "JWT",scopes = {"user","admin"})
    Response updatePassword(@Parameter(description = "nombre del usuario al que se desea actualizar la clave", example = "pedro",required = true)
                    @PathParam("username") @NotBlank String username,
                            @RequestBody(description = "Claves necesarias para la actualización", required = true,
                            content = @Content(schema = @Schema(ref = "Clave")))
                    @Nonnull @Valid PasswordUpdateDTO passwordUpdateDTO);

    @Operation(
            summary = "obtener datos de usuario",
            description = "Permite obtener los datos de un usuario",operationId = "UsuarioAPI :: consultar usuario"

    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Usuario"))
            ),
            @APIResponse(responseCode = "401",description = "Usuario no autorizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Usuario no autorizado\"}]")
            ),
            @APIResponse(responseCode = "403",description = "No tiene permitido realizar la operación",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"No tiene permitido realizar la operación\"}]")
            ),
            @APIResponse(responseCode = "404",description = "Recurso no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Recurso no encontrado\"}]")
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Ocurrió un error interno al procesar la solicitud\"}]")
            )
    })
    @SecurityRequirement(name = "JWT",scopes = {"user","admin"})
    Response get(@Parameter(description = "nombre del usuario del que se desea obtener los datos", example = "pedro",required = true) @NotBlank @PathParam("username") String username);

    @Operation(
            summary = "lista los usuarios",
            description = "Permite obtener un listado de los usuarios registrados en el sistema, es posible filtrarlos por rol y por el nombre de usuario, de igual forma es posible ordenarlos ASC o DESC",
            operationId = "UsuarioAPI :: listar usuario"
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Usuarios"))
            ),
            @APIResponse(responseCode = "401",description = "Usuario no autorizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Usuario no autorizado\"}]")
            ),
            @APIResponse(responseCode = "403",description = "No tiene permitido realizar la operación",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"No tiene permitido realizar la operación\"}]")
            ),
            @APIResponse(responseCode = "404",description = "Recurso no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Recurso no encontrado\"}]")
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"),example = "[{\"error\":\"Ocurrió un error interno al procesar la solicitud\"}]")
            )
    })
    @SecurityRequirement(name = "JWT",scopes = {"admin"})
    Collection<Usuario> list(@Parameter(name = "usuario", description = "nombre del usuario por el que se desea filtrar", example = "pedro") @QueryParam("usuario") String username,
                             @Parameter(description = "rol por el que se desea filtrar, si es especificado solo se mostrarán aquellos usuarios que posean dicho rol.", example = "admin")
                             @QueryParam("rol") String rol,
                             @Parameter(description = "El listado es ordenado por nombre de usuario, order permite indicar si se desea en orden ascendente (ASC) o descendente (DESC).", example = "DESC")
                             @QueryParam("order") String order);
}
