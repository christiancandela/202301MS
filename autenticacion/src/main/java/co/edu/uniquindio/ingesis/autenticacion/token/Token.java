package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.seguridad.TokenUtilFactory;
import jakarta.json.bind.annotation.JsonbProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;


public class Token implements Serializable {
    @JsonbProperty("token")
    private String token;
    @JsonbProperty("vigencia")
    private LocalDateTime expirationDate;
    @JsonbProperty("usuario")
    private String userName;

    private static final int TIME_LIVE_LIMIT = 5 * 60;

    public Token() {
    }

    private Token(String userName) {
        this.userName = userName;
        expirationDate = LocalDateTime.now().plusSeconds(TIME_LIVE_LIMIT);
        this.token = TokenUtilFactory.getDefault().of().create(userName, Set.of("user"));
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
