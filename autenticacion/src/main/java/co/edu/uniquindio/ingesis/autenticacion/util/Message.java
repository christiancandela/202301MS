package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.json.bind.annotation.JsonbProperty;

import java.io.Serializable;

public class Message implements Serializable {
    @JsonbProperty("mensaje")
    private final String message;

    private Message(String message) {
        this.message = message;
    }

    public static Message of(String message) {
        return new Message(message);
    }
}
