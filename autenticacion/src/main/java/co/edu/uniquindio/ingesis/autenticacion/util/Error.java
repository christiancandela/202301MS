package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


/**
 * Registro que representa la información de un error, es usado para representar dichos errores y ser transformado a JSON.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 *
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
@Schema(implementation = Error.class,name = "Error",
        example = "{\"error\":\"Se produjo un error al procesar la solicitud\"}"
)
public record Error(@Schema(name = "error", description = "Descripción del error ocurrido") @JsonbProperty("error")String messages){
    public static Error of(String message){
        return new Error(message);
    }
}

/**
 * Entidad creada solo con propósitos de documentación del API. Modela un arreglo de Errores.
 */
@Schema(type = SchemaType.ARRAY,implementation = Error.class,name = "Errores",
        example = "[{\"error\":\"Se produjo un error al procesar la solicitud\"}]"
)
record Errores(){}