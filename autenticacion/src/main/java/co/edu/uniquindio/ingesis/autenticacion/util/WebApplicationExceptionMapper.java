package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Collections;
import java.util.List;


@Provider
public class WebApplicationExceptionMapper
        implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(final WebApplicationException exception) {
        List<Error> errorMessages = Collections.singletonList( Error.of(exception.getMessage()) );
        final var status = exception.getResponse() != null ?
                exception.getResponse().getStatusInfo().toEnum() :
                Response.Status.INTERNAL_SERVER_ERROR;
        return Response
                .status(status)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(errorMessages)
                .build();
    }

}