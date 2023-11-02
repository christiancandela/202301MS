package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

@Schema(implementation = Message.class,name = "Message",
        example = "{\"error\":\"Se produjo un error al procesar la solicitud\"}"
)
public record Message (@JsonbProperty("mensaje") @NotBlank String message) implements Serializable {
    public static Message of(String message) {
        return new Message(message);
    }
}
