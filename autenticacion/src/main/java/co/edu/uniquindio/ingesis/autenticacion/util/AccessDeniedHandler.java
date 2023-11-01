package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;

@Provider
public class AccessDeniedHandler implements ExceptionMapper<AccessDeniedException> {
    public Response toResponse(AccessDeniedException exception) {
        // Construct+return the response here...
        List<Error> errorMessages = Collections.singletonList( Error.of(exception.getMessage()) );
        return Response
                .status( Response.Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(errorMessages)
                .build();
    }
}