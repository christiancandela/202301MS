package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginDTO {
    private final String usuario;
    private final String clave;

    private LoginDTO(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

}
