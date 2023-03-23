package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.seguridad.TokenUtil;
import jakarta.json.bind.annotation.JsonbProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class Token implements Serializable {
    @JsonbProperty("token")
    private String token;
    @JsonbProperty("vigencia")
    private LocalDateTime expirationDate;
    @JsonbProperty("usuario")
    private String userName;

    private static final int TIME_LIVE_LIMIT = 5;

    public Token() {
    }

    private Token(String userName) {
        this.userName = userName;
        expirationDate = LocalDateTime.now().plusMinutes(TIME_LIVE_LIMIT);
        this.token = TokenUtil.create(userName, Set.of("user"),
                Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant())
        );
    }

    public static Token of(String userName){
        return new Token(userName);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
