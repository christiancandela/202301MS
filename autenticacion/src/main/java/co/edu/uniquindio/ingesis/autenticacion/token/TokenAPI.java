package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.usuario.Usuario;
import co.edu.uniquindio.ingesis.autenticacion.util.Message;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
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
            description = "Permite generar un token a partir de las credenciales (usuario y clave) de un usuario registrado en el sistema"

    )
    @APIResponses(value = {
            @APIResponse(responseCode = "201",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Token.class))
            ),
            @APIResponse(responseCode = "400",description = "La solicitud está incompleta, el usuario y la clave son requeridas",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "401",description = "Nombre de usuario o clave incorrectas.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            ),
            @APIResponse(responseCode = "500",description = "Error interno",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
            )
    })
    Response create(
            @RequestBody(description = "Credenciales del usuario que desea que se cree el token", required = true,
                    content = @Content(schema = @Schema(implementation = Credential.class)))
            @Valid Credential credential);

//    @Operation(
//            summary = "Invalidar Token",
//            description = "Permite invalidar un token"
//    )
//    @APIResponses(value = {
//            @APIResponse(responseCode = "204",description = "Operación exitosa",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
//                            schema = @Schema(implementation = Message.class))
//            ),
//            @APIResponse(responseCode = "400",description = "La solicitud está incompleta, el id del token es requerido",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
//                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
//            ),
//            @APIResponse(responseCode = "401",description = "Usuario no autorizado",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
//                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
//            ),
//            @APIResponse(responseCode = "403",description = "No tiene permitido realizar la operación",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
//                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
//            ),
//            @APIResponse(responseCode = "404",description = "Recurso no encontrado",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
//                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
//            ),
//            @APIResponse(responseCode = "500",description = "Error interno",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
//                            schema = @Schema(type = SchemaType.ARRAY,implementation = Error.class))
//            )
//    })
//    @SecurityRequirement(name = "JWT",scopes = {"user","admin"})
//    Response delete(@Parameter(description = "Identificador UUID del token que se desea invalidar") @PathParam("id") String id);

    @Operation(
            summary = "Verificar token",
            description = "Permite obtener los datos de un token, permitiendo verficiar su validez"

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
    Response check(@Parameter(description = "Identificador UUID del token que se desea obtener") @PathParam("id") String id);

    @Operation(
            summary = "Listar tokens",
            description = "Permite obtener un listado de los token validos"

    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(type = SchemaType.ARRAY,implementation = Token.class),example = "[{\n" +
                            "  \"emision\": \"2023-11-02T15:55:47.933451\",\n" +
                            "  \"emisor\": \"https://grid.uniquindio.edu.co\",\n" +
                            "  \"id\": \"247b340c-74ee-4c89-8186-7013ede207bf\",\n" +
                            "  \"propiedades\": {},\n" +
                            "  \"roles\": [\n" +
                            "    \"admin\"\n" +
                            "  ],\n" +
                            "  \"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImdyb3VwcyI6ImFkbWluIiwiZXhwIjoxNjk4OTU4ODQ3LCJqdGkiOiIyNDdiMzQwYy03NGVlLTRjODktODE4Ni03MDEzZWRlMjA3YmYiLCJpc3MiOiJodHRwczovL2dyaWQudW5pcXVpbmRpby5lZHUuY28iLCJpYXQiOjE2OTg5NTg1NDd9.D6DF_It32Vk07TX7RSjSA17CK170nDutm7DqX-AVyQ--uloSKkWD0Daj3nlPYntjz0gX8vqUGE9nec-Fy5si2PLW-Ke_J12x4O7_NzMA3uoD2ILuz5kT6U5dcB6tACX58A1Vg69ifW8NCngl_1FD5iN3HWEq5rMCAWnbqpPKA3n-MQwLHnBz7qqviV-Nw8JkRvhrCzM5ptO177dMV-9dMeKReilp1kKcLHGX6QzDveHZTFJTtmNSgbJ_HjEv23KRtErX1V9C1asqDWZupgG7VwPvcUj88FD-ej2AaSK3JLd-fBYNutS_TE9dFZdmfR1KLQrcVGJYgs5iCv7qhzQm9w\",\n" +
                            "  \"usuario\": \"admin\",\n" +
                            "  \"vigencia\": \"2023-11-02T16:00:47.933367\"\n" +
                            "}]")
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
    Collection<Token> list();
}
