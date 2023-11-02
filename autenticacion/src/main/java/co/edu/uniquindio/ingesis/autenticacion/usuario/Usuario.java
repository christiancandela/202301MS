package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.repository.Entity;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record Usuario(String id,
                      @JsonbProperty("usuario") String username,
                      @JsonbProperty("clave") String password,
                      @JsonbProperty("roles") Set<String> roles
) implements Entity {
    public Usuario{
        if( id == null){
            id = UUID.randomUUID().toString();
        }
    }

    public static Usuario.UsuarioBuilder builder() {
        return new Usuario.UsuarioBuilder();
    }
}
