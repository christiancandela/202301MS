package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.repository.Entity;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Builder
public record Token (String id,
                     @JsonbProperty("token") String token,
                     @JsonbProperty("vigencia") LocalDateTime expirationDate,
                     @JsonbProperty("usuario") String userName,
                     @JsonbProperty("roles") Set<String> rols,
                     @JsonbProperty("emisor") String issuer,
                     @JsonbProperty("emision") LocalDateTime issuerDate,
                     @JsonbProperty("propiedades") Map<String,Object> attributes
) implements Entity {
    public static TokenBuilder builder() {
        return new TokenBuilder();
    }
}
