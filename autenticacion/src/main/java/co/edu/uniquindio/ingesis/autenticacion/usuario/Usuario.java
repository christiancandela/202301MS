package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.repository.Entity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.DiscriminatorMapping;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;

import java.util.Set;
import java.util.UUID;

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

    public static Usuario.UsuarioBuilder builder() {
        return new Usuario.UsuarioBuilder();
    }
}
