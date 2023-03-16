package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos;

import lombok.Builder;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

@Getter
@Builder
public class TokenDTO {
    private final String token;
    private final LocalDateTime vigencia;
    private final String usuario;

    @ConstructorProperties({"token", "vigencia","usuario"})
    public TokenDTO(String token, LocalDateTime vigencia, String usuario) {
        this.token = token;
        this.vigencia = vigencia;
        this.usuario = usuario;
    }
}
