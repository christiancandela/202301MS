package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.usuario.Usuario;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.Collection;

public interface TokenAPI {
    @Operation(
            summary = "Registrar usuario",
            description = "Permite registrar un usuario en el sistema"

    )
    @APIResponses(value = {
            @APIResponse(responseCode = "201",description = "Operación exitosa",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Token.class))
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
    Response create(
            @RequestBody(description = "Credenciales del usuario que desea que se cree el token", required = true,
                    content = @Content(schema = @Schema(implementation = Credential.class)))
            @Valid Credential credential);

    @DELETE
    @Path("{id}")
    @RolesAllowed({"user", "admin"})
    Response delete(@PathParam("id") String id);

    @GET
    @Path("{id}")
    @RolesAllowed({"user", "admin"})
    Response check(@PathParam("id") String id);

    @GET
    @RolesAllowed({"admin"})
    Collection<Token> list();
}
