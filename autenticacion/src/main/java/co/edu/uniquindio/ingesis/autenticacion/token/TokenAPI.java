package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
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

public interface TokenAPI {
    @Operation(
            summary = "Generar Token",
            description = "Permite generar un token a partir de las credenciales (usuario y clave) de un usuario registrado en el sistema",
            operationId = "TokenAPI :: generar"
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "201",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Token"))
            ),
            @APIResponse(responseCode = "400",description = "La solicitud está incompleta, el usuario y la clave son requeridas",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            ),
            @APIResponse(responseCode = "401",description = "Nombre de usuario o clave incorrectas.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            )
    })
    Response create(
            @RequestBody(description = "Credenciales del usuario que desea que se cree el token", required = true,
                    content = @Content(schema = @Schema(ref = "Credential")))
            @Valid Credential credential);

    @Operation(
            summary = "Verificar token",
            description = "Permite obtener los datos de un token, permitiendo verificar su validez",
            operationId = "TokenAPI :: verificar token"
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Token"))
            ),
            @APIResponse(responseCode = "401",description = "Usuario no autorizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            ),
            @APIResponse(responseCode = "403",description = "No tiene permitido realizar la operación",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            ),
            @APIResponse(responseCode = "404",description = "Recurso no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            )
    })
    @SecurityRequirement(name = "JWT",scopes = {"user","admin"})
    Response check(@Parameter(description = "Identificador UUID del token que se desea obtener",required = true) @PathParam("id") String id);

    @Operation(
            summary = "Listar tokens",
            description = "Permite obtener un listado de los token validos",
            operationId = "TokenAPI :: listar tokens"
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Tokens"))
            ),
            @APIResponse(responseCode = "401",description = "Usuario no autorizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            ),
            @APIResponse(responseCode = "403",description = "No tiene permitido realizar la operación",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            ),
            @APIResponse(responseCode = "404",description = "Recurso no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(ref = "Errores"))
            )
    })
    @SecurityRequirement(name = "JWT",scopes = {"user","admin"})
    Collection<Token> list();
}
