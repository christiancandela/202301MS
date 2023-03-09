package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;

public class Credential {
    @JsonbProperty("usuario")
    @NotBlank(message = "El nombre de usuario es obligatorio.")
    private String userName;
    @JsonbProperty("clave")
    @NotBlank(message = "La clave es obligatoria.")
    private String password;

    public Credential() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
