package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.repository.Entity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.security.enterprise.identitystore.PasswordHash;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Builder
public record Usuario(String id,
                      @JsonbProperty("usuario") String username,
                      @JsonbProperty("clave") String password,
                      @JsonbProperty("roles") Set<String> rols
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
