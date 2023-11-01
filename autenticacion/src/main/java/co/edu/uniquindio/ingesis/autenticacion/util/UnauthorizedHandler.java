package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Collections;
import java.util.List;

@Provider
public class UnauthorizedHandler implements ExceptionMapper<NotAuthorizedException> {
    public Response toResponse(NotAuthorizedException exception) {
        // Construct+return the response here...
        List<Error> errorMessages = Collections.singletonList( Error.of(exception.getMessage()) );
        return Response
                .status( Response.Status.UNAUTHORIZED)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(errorMessages)
                .build();
    }
}