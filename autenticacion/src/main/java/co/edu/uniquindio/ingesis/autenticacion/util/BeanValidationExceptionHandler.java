package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Clase encargada de mapear las excepciones de tipo {@link ConstraintViolationException}, capturándolas y
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
public class BeanValidationExceptionHandler
        implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        List<Error> errorMessages = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .map(Error::of)
                .collect(Collectors.toList());
        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(errorMessages)
                .build();
    }

}