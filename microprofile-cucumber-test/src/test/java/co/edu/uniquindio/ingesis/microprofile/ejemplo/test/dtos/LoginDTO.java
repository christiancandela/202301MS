package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos;

public class LoginDTO {
    private final String usuario;
    private final String clave;

    private LoginDTO(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public static LoginDTO of(String usuario, String clave) {
        return new LoginDTO(usuario, clave);
    }
}
