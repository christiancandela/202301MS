package co.edu.uniquindio.ingesis.autenticacion.usuario;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
/**
 * Clase que representa los datos requeridos para el cambio de clave (clave actual, nueva clave, verificación de la nueva clave), además incluye elementos que permiten su transformación a JSON.
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
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
