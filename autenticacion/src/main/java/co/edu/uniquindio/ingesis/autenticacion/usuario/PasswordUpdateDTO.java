package co.edu.uniquindio.ingesis.autenticacion.usuario;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(implementation = PasswordUpdateDTO.class,name = "Clave",
        example = "{\"clave\":\"12345\",\"nueva_clave\":\"ABC123\",\"verificacion_clave\":\"ABC123\"}"
)
public record PasswordUpdateDTO(
                          @Schema(name = "clave", description = "Nueva clave a ser asignada al usuario")
                          @NotBlank
                          @JsonbProperty("clave")String password,
                          @Schema(name = "nueva_clave", description = "Nueva clave a ser asignada al usuario")
                          @NotBlank
                          @JsonbProperty("nueva_clave")String newPassword,
                          @Schema(name = "verificacion_clave", description = "Nueva clave repetida para verificación")
                          @NotBlank
                          @JsonbProperty("verificacion_clave")String verifyPassword) {
}
