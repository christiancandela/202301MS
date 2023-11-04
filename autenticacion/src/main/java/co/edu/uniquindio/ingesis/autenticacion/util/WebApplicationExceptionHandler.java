package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Collections;
import java.util.List;

/**
 * Clase encargada de mapear las excepciones de tipo {@link WebApplicationException}, capturándolas y
 * cambiando el formato de presentación a json antes de enviarlas como respuesta.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 *
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
@Provider
public class WebApplicationExceptionHandler
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