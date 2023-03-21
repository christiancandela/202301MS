package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dto;

import java.beans.ConstructorProperties;
import java.io.Serializable;

public class UsuarioDTO implements Serializable {
    private final String usuario;
    private final String clave;



    @ConstructorProperties({"usuario","clave"})
    private UsuarioDTO(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public static UsuarioDTO of(String usuario, String clave) {
        return new UsuarioDTO(usuario, clave);
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }
}
