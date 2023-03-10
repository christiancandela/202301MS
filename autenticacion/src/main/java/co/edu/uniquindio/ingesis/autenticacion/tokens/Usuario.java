package co.edu.uniquindio.ingesis.autenticacion.tokens;

import javax.validation.constraints.NotBlank;

public class Usuario {
    @NotBlank(message = "El nombre de usuario es obligatorio.")
    private String usuario;
    @NotBlank(message = "La clave es obligatoria.")
    private String clave;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
