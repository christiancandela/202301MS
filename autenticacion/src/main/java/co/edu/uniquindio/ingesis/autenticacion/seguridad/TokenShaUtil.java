package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/**
 * Clase utilitaria usada para la generación de tokens, de forma particular está clase implementa los tokens mediante el algoritmo SHA.
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
public class TokenShaUtil extends TokenUtil {
    /**
     * Constructor que permite inicializar el tiempo de vida del token y la entidad generadora.
     * @param timeOfLife Tiempo de vida del token
     * @param issuer Entidad generadora del token
     */
    public TokenShaUtil(long timeOfLife, String issuer) {
        super(timeOfLife, issuer);
    }


    /**
     * Constructor por defecto.
     */
    public TokenShaUtil() {
        super();
    }

    /**
     * @see TokenUtil#loadSignatureKey()
     */
    @Override
    protected Key loadSignatureKey() {
        var keyLoaderUtil = new KeyLoaderUtil("mp.jwt.verify.publickey",
                "mp.jwt.verify.publickey.location");
        return keyLoaderUtil.loadKeyAsString().map(this::parse).orElse( Jwts.SIG.HS512.key().build() );
    }

    /**
     * Método encargado de transformar la representación String de la llave privada a Key
     * @return Key que representa la llave privada a ser usada para la generación de los tokens
     */
    public Key parse(String keyAsString) {
        var byteKey = keyAsString.getBytes();
        return Keys.hmacShaKeyFor(byteKey);
    }
}
