package co.edu.uniquindio.ingesis.autenticacion.tokens;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Token {
    private String token;
    private String usuario;
    private String vigencia;

    public Token(String usuario) {
        this.usuario = usuario;
        token = UUID.randomUUID().toString();
        vigencia = LocalDateTime.now().plus(5, ChronoUnit.MINUTES).toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }
}
