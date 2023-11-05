package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Clase que representa los datos básicos (Credenciales {username y password}) para realizar una operación de autenticación (generación de token),
 * además incluye elementos que permiten su transformación a JSON.
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
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
