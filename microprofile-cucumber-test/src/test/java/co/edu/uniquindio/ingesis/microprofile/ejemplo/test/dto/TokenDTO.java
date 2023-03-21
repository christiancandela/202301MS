package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dto;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

public class TokenDTO {
    private final String token;
    private final String usuario;
    private final LocalDateTime vigencia;

    @ConstructorProperties({"token","usuario","vigencia"})
    public TokenDTO(String token, String usuario, LocalDateTime vigencia) {
        this.token = token;
        this.usuario = usuario;
        this.vigencia = vigencia;
    }

    public String getToken() {
        return token;
    }

    public String getUsuario() {
        return usuario;
    }

    public LocalDateTime getVigencia() {
        return vigencia;
    }
}
