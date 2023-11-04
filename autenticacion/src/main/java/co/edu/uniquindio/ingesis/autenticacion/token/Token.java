package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.repository.Entity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Builder
@Schema(implementation = Token.class,name = "Token",
        example = "{\n" +
                "  \"emision\": \"2023-11-02T15:55:47.933451\",\n" +
                "  \"emisor\": \"https://grid.uniquindio.edu.co\",\n" +
                "  \"id\": \"247b340c-74ee-4c89-8186-7013ede207bf\",\n" +
                "  \"propiedades\": {},\n" +
                "  \"roles\": [\n" +
                "    \"admin\"\n" +
                "  ],\n" +
                "  \"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImdyb3VwcyI6ImFkbWluIiwiZXhwIjoxNjk4OTU4ODQ3LCJqdGkiOiIyNDdiMzQwYy03NGVlLTRjODktODE4Ni03MDEzZWRlMjA3YmYiLCJpc3MiOiJodHRwczovL2dyaWQudW5pcXVpbmRpby5lZHUuY28iLCJpYXQiOjE2OTg5NTg1NDd9.D6DF_It32Vk07TX7RSjSA17CK170nDutm7DqX-AVyQ--uloSKkWD0Daj3nlPYntjz0gX8vqUGE9nec-Fy5si2PLW-Ke_J12x4O7_NzMA3uoD2ILuz5kT6U5dcB6tACX58A1Vg69ifW8NCngl_1FD5iN3HWEq5rMCAWnbqpPKA3n-MQwLHnBz7qqviV-Nw8JkRvhrCzM5ptO177dMV-9dMeKReilp1kKcLHGX6QzDveHZTFJTtmNSgbJ_HjEv23KRtErX1V9C1asqDWZupgG7VwPvcUj88FD-ej2AaSK3JLd-fBYNutS_TE9dFZdmfR1KLQrcVGJYgs5iCv7qhzQm9w\",\n" +
                "  \"usuario\": \"admin\",\n" +
                "  \"vigencia\": \"2023-11-02T16:00:47.933367\"\n" +
                "}")
public record Token (@Schema(description = "Identificador UUID del token generado", type = SchemaType.STRING) @NotBlank String id,
                     @Schema(description = "Token de seguridad generado para el usuario") @NotBlank @JsonbProperty("token") String token,
                     @Schema(name = "vigencia", description = "Contiene el momento en que el token perderá su validez",example = "2023-11-02T16:00:47.933367")
                     @NotNull
                     @JsonbProperty("vigencia") LocalDateTime expirationDate,
                     @Schema(name = "usuario",description = "Usuario para el que se genero el token", example = "pedro")
                     @NotBlank
                     @JsonbProperty("usuario") String userName,
                     @Schema(name = "roles", type = SchemaType.ARRAY,implementation = String.class,description = "Contiene el listado de roles del usuario",example = "[\"user\"]")
                     @JsonbProperty("roles") Set<String> roles,
                     @Schema(name = "emisor",description = "Nombre de la entidad que emite el token", example = "grid.uniquindio.edu.co")
                     @NotBlank
                     @JsonbProperty("emisor") String issuer,
                     @Schema(name = "emision", description = "Contiene el momento en que el token fue emitido",example = "2023-11-02T16:00:47.933367")
                     @JsonbProperty("emision") LocalDateTime issuerDate,
                     @Schema(name = "propiedades",description = "Conjunto de propiedades adicionales que se almacenan en el token")
                     @JsonbProperty("propiedades") Map<String,Object> attributes
) implements Entity {
    public static TokenBuilder builder() {
        return new TokenBuilder();
    }
}

@Schema(type = SchemaType.ARRAY,implementation = Token.class,example = "[{\n" +
        "  \"emision\": \"2023-11-02T15:55:47.933451\",\n" +
        "  \"emisor\": \"https://grid.uniquindio.edu.co\",\n" +
        "  \"id\": \"247b340c-74ee-4c89-8186-7013ede207bf\",\n" +
        "  \"propiedades\": {},\n" +
        "  \"roles\": [\n" +
        "    \"admin\"\n" +
        "  ],\n" +
        "  \"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImdyb3VwcyI6ImFkbWluIiwiZXhwIjoxNjk4OTU4ODQ3LCJqdGkiOiIyNDdiMzQwYy03NGVlLTRjODktODE4Ni03MDEzZWRlMjA3YmYiLCJpc3MiOiJodHRwczovL2dyaWQudW5pcXVpbmRpby5lZHUuY28iLCJpYXQiOjE2OTg5NTg1NDd9.D6DF_It32Vk07TX7RSjSA17CK170nDutm7DqX-AVyQ--uloSKkWD0Daj3nlPYntjz0gX8vqUGE9nec-Fy5si2PLW-Ke_J12x4O7_NzMA3uoD2ILuz5kT6U5dcB6tACX58A1Vg69ifW8NCngl_1FD5iN3HWEq5rMCAWnbqpPKA3n-MQwLHnBz7qqviV-Nw8JkRvhrCzM5ptO177dMV-9dMeKReilp1kKcLHGX6QzDveHZTFJTtmNSgbJ_HjEv23KRtErX1V9C1asqDWZupgG7VwPvcUj88FD-ej2AaSK3JLd-fBYNutS_TE9dFZdmfR1KLQrcVGJYgs5iCv7qhzQm9w\",\n" +
        "  \"usuario\": \"admin\",\n" +
        "  \"vigencia\": \"2023-11-02T16:00:47.933367\"\n" +
        "}]")
record Tokens(){}