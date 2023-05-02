package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.eclipse.microprofile.config.ConfigProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

// https://github.com/jwtk/jjwt/tree/0.11.2
public class TokenRSAUtil extends TokenUtil{
    private static final KeyPair DEFAULT_KEY_PAIR = Keys.keyPairFor(SignatureAlgorithm.RS256);
    public TokenRSAUtil(long timeOfLife, String issuer) {
        super(timeOfLife, issuer, loadPrivateKey(), loadKey(),SignatureAlgorithm.RS256 );
    }

    public TokenRSAUtil() {
        super(loadPrivateKey(), loadKey(),SignatureAlgorithm.RS256 );
    }

    private static Key loadKey() {
        Key key;
        try {
            key = publickeyFromConfiguration().orElse(DEFAULT_KEY_PAIR.getPublic());
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("No se pudo cargar la llave", exception);
        }
        return key;
    }
    private static Key loadPrivateKey() {
        Key key;
        try {
            key = privateKeyFromConfiguration().orElse(DEFAULT_KEY_PAIR.getPrivate());
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("No se pudo cargar la llave", exception);
        }
        return key;
    }

    private static Optional<Key> publickeyFromConfiguration() throws Exception {
        var optionalKey = ConfigProvider.getConfig().getOptionalValue("mp.jwt.verify.publickey", String.class);
        String secretKey = optionalKey.orElse(readFromFile());
        if (secretKey != null) {
            var byteKey = Base64.getDecoder().decode(removeBeginEnd( secretKey ) );
            var encodedKey = new X509EncodedKeySpec(byteKey);
            return Optional.of( KeyFactory.getInstance("RSA").generatePublic(encodedKey) );
        }
        return Optional.empty();
    }

    private static Optional<Key> privateKeyFromConfiguration() throws Exception {
        var optionalKey = ConfigProvider.getConfig().getOptionalValue("mp.jwt.verify.privatekey", String.class);
        String secretKey = optionalKey.orElse(readPrivateKeyFromFile());
        if (secretKey != null) {
            var byteKey = Base64.getDecoder().decode(removeBeginEnd( secretKey ) );
            var encodedKey = new PKCS8EncodedKeySpec(byteKey);
            return Optional.of( KeyFactory.getInstance("RSA").generatePrivate(encodedKey) );
        }
        return Optional.empty();
    }

    private static  String readFromFile() throws URISyntaxException, IOException {
        return readFromFileConfigurated("mp.jwt.verify.publickey.location");
    }

    private static  String readFromFileConfigurated(String key) throws URISyntaxException, IOException {
        var optionalKey = ConfigProvider.getConfig().getOptionalValue(key, String.class);
        if (optionalKey.isPresent()) {
            return readFromFile(optionalKey.get());
        }
        return null;
    }


    private static String readPrivateKeyFromFile() throws URISyntaxException, IOException {
        return readFromFileConfigurated("mp.jwt.verify.privatekey.location");
    }

}
