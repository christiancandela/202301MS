package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import lombok.Getter;

@Getter
public enum Rol {
    ADMIN("admin"),
    USER("user");
    public static final String ADMIN_ROL = "admin";
    public static final String USER_ROL = "rol";
    private final String name;

    Rol(String name) {
        this.name = name;
    }
}
