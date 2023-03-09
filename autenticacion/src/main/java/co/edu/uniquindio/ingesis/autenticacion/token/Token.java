package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.json.bind.annotation.JsonbProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Token implements Serializable {
    @JsonbProperty("token")
    private String uuid;
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
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }


    public String getUserName() {
        return userName;
    }

    public static Token of(String userName){
        return new Token(userName);
    }


    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
