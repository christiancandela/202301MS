package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Collections;
import java.util.List;


@Provider
public class GeneralExceptionMapper
        implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(final Exception exception) {
        List<Error> errorMessages = Collections.singletonList( Error.of("Se ha presentado un error inesperado en el sistema: "+exception.getMessage()) );
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(errorMessages)
                .build();
    }

}