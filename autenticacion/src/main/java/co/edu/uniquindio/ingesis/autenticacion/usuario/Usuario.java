package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.repository.Entity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

/**
 * Clase que representa los datos básicos de un usuario (id, username, clave, roles), además incluye elementos que permiten su transformación a JSON.
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
@Builder
@Schema(implementation = Usuario.class,name = "Usuario",
        example = "{\"usuario\":\"pedro\",\"clave\":\"12345\",\"roles\":[\"user\"]}")
public record Usuario(String id,
                      @Schema(name = "usuario",example = "pedro") @JsonbProperty("usuario") @NotBlank String username,
                      @Schema(name = "clave",example = "12345") @JsonbProperty("clave") @NotBlank String password,
                      @Schema(name ="roles", example = "[\"user\"]", implementation = String.class) @JsonbProperty("roles") Set<String> roles
) implements Entity {
    public Usuario{
        if( id == null){
            id = UUID.randomUUID().toString();
        }
    }

    /**
     * Permite obtener un builder de {@link Usuario}
     * @return Builder para el Usuario
     */
    public static Usuario.UsuarioBuilder builder() {
        return new Usuario.UsuarioBuilder();
    }
}

/**
 * Entidad creada solo con propósitos de documentación del API. Modela un arreglo de Usuarios.
 */
@Schema(name = "Usuarios", type = SchemaType.ARRAY,implementation = Usuario.class, example = "[{\"usuario\":\"pedro\",\"clave\":\"12345\",\"roles\":[\"user\"]}]")
record Usuarios(){}