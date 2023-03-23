package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Token implements Serializable {
    @Id
    @JsonbProperty("token")
    @Column(length = 50)
    private String token;
    @JsonbProperty("vigencia")
    private LocalDateTime expirationDate;
    @JsonbProperty("usuario")
    @Column(length = 50)
    private String userName;

    private static final int TIME_LIVE_LIMIT = 5;

    public Token() {
    }

    private Token(String userName) {
        this.userName = userName;
        this.token = UUID.randomUUID().toString();
        expirationDate = LocalDateTime.now().plusMinutes(TIME_LIVE_LIMIT);
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
