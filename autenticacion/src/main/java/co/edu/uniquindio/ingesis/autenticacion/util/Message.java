package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

/**
 * Registro que representa la información de un mensaje a ser envíado al usuario como respuesta al procesamiento de una operación, es usado para representar dichos mensajes y ser transformado a JSON.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
@Schema(implementation = Message.class, name = "Message", example = "{\"error\":\"Se produjo un error al procesar la solicitud\"}")
public record Message(@JsonbProperty("mensaje") @NotBlank String message) implements Serializable {
    public static Message of(String message) {
        return new Message(message);
    }
}
