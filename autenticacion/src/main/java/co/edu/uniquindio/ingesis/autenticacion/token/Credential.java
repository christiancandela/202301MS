package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(implementation = Credential.class,name = "Credential",
        example = "{\"usuario\":\"pedro\",\"clave\":\"12345\"}")
public record  Credential (
    @Schema(name = "usuario",example = "pedro")
    @JsonbProperty("usuario")
    @NotBlank(message = "El nombre de usuario es obligatorio.")
    String username,
    @Schema(name = "clave",example = "12345")
    @JsonbProperty("clave")
    @NotBlank(message = "La clave es obligatoria.")
    String password ) {


}
