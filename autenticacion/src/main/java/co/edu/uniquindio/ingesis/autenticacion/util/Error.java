package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Schema(implementation = Error.class,name = "Error",
        example = "{\"error\":\"Se produjo un error al procesar la solicitud\"}"
)
public record Error(@Schema(name = "error", description = "Descripción del error ocurrido") @JsonbProperty("error")String messages){
    public static Error of(String message){
        return new Error(message);
    }
}

@Schema(type = SchemaType.ARRAY,implementation = Error.class,name = "Errores",
        example = "[{\"error\":\"Se produjo un error al procesar la solicitud\"}]"
)
record Errores(){}