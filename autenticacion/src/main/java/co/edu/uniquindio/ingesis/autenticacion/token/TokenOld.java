package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.json.bind.annotation.JsonbProperty;
import org.eclipse.microprofile.config.ConfigProvider;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class TokenOld implements Serializable {
    private String id;
    @JsonbProperty("token")
    private String token;
    @JsonbProperty("vigencia")
    private LocalDateTime expirationDate;
    @JsonbProperty("usuario")
    private String userName;
    @JsonbProperty("roles")
    private Set<String> rols;
    @JsonbProperty("emisor")
    private String issuer;
    @JsonbProperty("emision")
    private LocalDateTime issuerDate;
    @JsonbProperty("propiedades")
    private Map<String,Object> attributes;
    private static final int TIME_LIVE_LIMIT = 5 * 60;

    public TokenOld() {
        this("nouser");
    }

    private TokenOld(String userName) {
        this(userName,Set.of("user"));
    }

    public TokenOld(String userName, Set<String> rols){
        this(userName,rols,
                LocalDateTime.now().plusSeconds(
                        ConfigProvider.getConfig()
                                .getOptionalValue("mp.jwt.timeoflife",Long.class)
                                .orElse(60L)
                )
        );
    }

    public TokenOld(String userName, Set<String> rols, LocalDateTime expirationDate) {
        id = UUID.randomUUID().toString();
        issuer = ConfigProvider.getConfig()
                .getOptionalValue("mp.jwt.verify.issuer",String.class)
                .orElse("https://grid.uniquindio.edu.co");
        this.expirationDate = expirationDate;
        this.userName = userName;
        this.rols = rols;
        issuerDate = LocalDateTime.now();
    }


    public void addAttribute(String name,Object value){
        attributes.put(name,value);
    }
}
