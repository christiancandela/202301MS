package co.edu.uniquindio.ingesis.autenticacion.util;

import jakarta.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Schema(implementation = Error.class,name = "Error",
        example = """
                {
                    "error":"Se produjo un error al procesar la solicitud"
                }
                """
)
public record Error(@JsonbProperty("error")String messages){
    public static Error of(String message){
        return new Error(message);
    }
}