package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import lombok.Getter;
/**
 * Enumeración de los roles por defecto a usar en el sistema de autenticación, actualmente ADMIN y USER.
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
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
