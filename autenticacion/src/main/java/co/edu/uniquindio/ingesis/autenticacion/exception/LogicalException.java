package co.edu.uniquindio.ingesis.autenticacion.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class LogicalException extends WebApplicationException {
    public LogicalException() {
        this("No se pudo completar la operación");
    }

    public LogicalException(String message) {
        this(message,500);
    }

    public LogicalException(String message, int status) {
        super(message, status);
    }

    public LogicalException(String message, Response.Status status) {
        this(message, status.getStatusCode());
    }
}
