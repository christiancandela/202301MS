package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import co.edu.uniquindio.ingesis.autenticacion.token.Token;
import io.jsonwebtoken.Jwts;
import org.eclipse.microprofile.config.ConfigProvider;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Clase utilitaria usada para la generación de tokens.
 * <p>
 * <a href="https://github.com/jwtk/jjwt">libraría usada</a>
 * <p>
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
public abstract class TokenUtil {
    /**
     * Nombre de los grupos a usar en el Token.
     */
    private static final String ROLES_KEY = "groups" ;
    /**
     * Tiempo de vida que se dará a los tokens
     *
     */
    private final long timeOfLife;
    /**
     * Nombre de la entidad generadora del token
     */
    private final String issuer;
    /**
     * Llave usada para firmar el token.
     */
    private final Key signatureKey;

    /**
     * Constructo de la clase
     */
    public TokenUtil() {
        this(ConfigProvider.getConfig().getOptionalValue("mp.jwt.timeoflife",Long.class).orElse(60L),
             ConfigProvider.getConfig().getOptionalValue("mp.jwt.verify.issuer",String.class)
                     .orElse("https://grid.uniquindio.edu.co")
        );
    }

    /**
     * Constructor que permite inicializar el tiempo de vida del token y la entidad generadora.
     * @param timeOfLife Tiempo de vida del token
     * @param issuer Entidad generadora del token
     */
    public TokenUtil(long timeOfLife, String issuer) {
        this.timeOfLife = timeOfLife;
        this.issuer = issuer;
        this.signatureKey = loadSignatureKey();
    }

    /**
     * Permite cargar la llave a ser usada para firmar el token
     * @return La llave a usar para firmar el token
     */
    abstract protected Key loadSignatureKey();

    /**
     * Permite crear un token basado en los datos dados.
     * @param username Nombre del usuario para el que se genera el token
     * @param roles Roles a los que pertenece el usuario para el que se genera el token
     * @return Token generado
     */
    public Token create(String username, Set<String> roles) {
        return create(username, roles, getExpiration());
    }

    /**
     * Permite crear un token basado en los datos dados.
     * @param username Nombre del usuario para el que se genera el token
     * @param roles Roles a los que pertenece el usuario para el que se genera el token
     * @param expiration Momento en el que expira el token
     * @return Token generado
     */
    public Token create(String username, Set<String> roles, LocalDateTime expiration) {
        return create(username, roles, expiration, Collections.emptyMap());
    }

    /**
     * Permite crear un token basado en los datos dados.
     * @param username Nombre del usuario para el que se genera el token
     * @param roles Roles a los que pertenece el usuario para el que se genera el token
     * @param expiration Momento en el que expira el token
     * @param claims Conjunto de datos a incorporar en el token
     * @return Token generado
     */
    public Token create(String username, Set<String> roles, LocalDateTime expiration, Map<String, Object> claims) {
        if (expiration == null) {
            expiration = getExpiration();
        }

        LocalDateTime issueDate = LocalDateTime.now();

        final var id = UUID.randomUUID().toString();
        final String token = Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .signWith(signatureKey)
                .claims(claims)
                .subject(username)
                .claim(ROLES_KEY, String.join(",", roles))
                .expiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
                .id( id )
                .issuer(issuer)
                .issuedAt(Date.from(issueDate.atZone(ZoneId.systemDefault()).toInstant()))
                .compact();

        return Token.builder()
                .id( id )
                .token(token)
                .roles(roles)
                .issuer(issuer)
                .userName(username)
                .attributes(claims)
                .expirationDate(expiration)
                .issuerDate(issueDate)
                .build();
    }

    /**
     * Calcula la expiración basada en la fecha y hora actual y el tiempo de vida parametrizado.
     * @return LocalDateTime que contiene la fecha y hora de expiración del token.
     */
    private LocalDateTime getExpiration() {
        return LocalDateTime.now().plusSeconds(timeOfLife);
    }
}
