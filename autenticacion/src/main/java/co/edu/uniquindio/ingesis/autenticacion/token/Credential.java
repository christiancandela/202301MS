package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;

public record  Credential (
    @JsonbProperty("usuario")
    @NotBlank(message = "El nombre de usuario es obligatorio.")
    String userName,
    @JsonbProperty("clave")
    @NotBlank(message = "La clave es obligatoria.")
    String password ) {


}
