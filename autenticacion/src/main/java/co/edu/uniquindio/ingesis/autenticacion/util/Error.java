package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.json.bind.annotation.JsonbProperty;

public class Error{
    @JsonbProperty("error")
    public final String messages;
    private Error(String messages) {
        this.messages = messages;
    }
    public static Error of(String message){
        return new Error(message);
    }
}