package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase utilitaria usada para la generación de tokens, de forma particular está clase implementa los tokens mediante el algoritmo RSA.
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
public final class TokenRSAUtil extends TokenUtil{
    private static final Logger LOG = Logger.getLogger(TokenRSAUtil.class.getName());
    /**
     * LLave a user por defecto en caso de que no se proporcione una.
     */
    private static final KeyPair DEFAULT_KEY_PAIR = Jwts.SIG.RS256.keyPair().build();

    /**
     * Constructor que permite inicializar el tiempo de vida del token y la entidad generadora.
     * @param timeOfLife Tiempo de vida del token
     * @param issuer Entidad generadora del token
     */
    public TokenRSAUtil(long timeOfLife, String issuer) {
        super(timeOfLife, issuer );
    }

    /**
     * Constructor por defecto
     */
    public TokenRSAUtil() {
        super();
    }

    /**
     * @see TokenUtil#loadSignatureKey()
     */
    @Override
    protected Key loadSignatureKey() {
        var keyLoaderUtil = new KeyLoaderUtil("mp.jwt.verify.privatekey",
                "mp.jwt.verify.privatekey.location");
        return keyLoaderUtil.loadKeyAsString().map(this::parse).orElse( DEFAULT_KEY_PAIR.getPrivate() );
    }

    /**
     * Permite obtener la llave pública a ser usada
     * @return Key que representa la llave pública
     */
    public Key getPublicKey(){
        var keyLoaderUtil = new KeyLoaderUtil("mp.jwt.verify.publickey",
                "mp.jwt.verify.publickey.location");
        return keyLoaderUtil.loadKeyAsString().map(this::parserPublicKey).orElse( DEFAULT_KEY_PAIR.getPublic() );
    }

    /**
     * Método encargado de transformar la representación String de la llave privada a Key
     * @return Key que representa la llave privada a ser usada para la generación de los tokens
     */
    public Key parse(String keyAsString) {
        try {
            var byteKey = Base64.getDecoder().decode( keyAsString );
            var encodedKey = new PKCS8EncodedKeySpec(byteKey);
            return KeyFactory.getInstance("RSA").generatePrivate(encodedKey);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            LOG.log(Level.WARNING,e.getLocalizedMessage(),e);
            throw new RuntimeException("No se pudo cargar la llave",e);
        }
    }

    /**
     * Método encargado de transformar la representación String de la llave pública a Key
     * @return Key que representa la llave pública a ser usada para la generación de los tokens
     */
    private Key parserPublicKey(String keyAsString) {
        try {
            var byteKey = Base64.getDecoder().decode(keyAsString);
            var encodedKey = new X509EncodedKeySpec(byteKey);
            return KeyFactory.getInstance("RSA").generatePublic(encodedKey);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            LOG.log(Level.WARNING,e.getLocalizedMessage(),e);
            throw new RuntimeException(e);
        }
    }
}
