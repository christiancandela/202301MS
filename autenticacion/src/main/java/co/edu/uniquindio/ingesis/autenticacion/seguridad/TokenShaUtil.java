package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.eclipse.microprofile.config.ConfigProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Key;
import java.util.Optional;

// https://github.com/jwtk/jjwt/tree/0.11.2
public class TokenShaUtil extends TokenUtil {

    public TokenShaUtil(long timeOfLife, String issuer) {
        super(timeOfLife, issuer, loadKey(), loadKey(), SignatureAlgorithm.HS512);
    }

    public TokenShaUtil() {
        super(loadKey(), loadKey(), SignatureAlgorithm.HS512);
    }

    private static Key loadKey() {
        Key key;
        try {
            key = keyFromConfiguration().orElse(Keys.secretKeyFor(SignatureAlgorithm.HS512));
        } catch (Exception exception) {
            throw new RuntimeException("No se pudo cargar la llave", exception);
        }
        return key;
    }

    private static Optional<Key> keyFromConfiguration() throws URISyntaxException, IOException {
        var optionalKey = ConfigProvider.getConfig().getOptionalValue("mp.jwt.verify.publickey", String.class);
        String secretKey = optionalKey.orElse(readFromFile());
        if (secretKey != null) {
            var byteKey = removeBeginEnd(secretKey).getBytes();
            return Optional.of( Keys.hmacShaKeyFor(byteKey) );
        }
        return Optional.empty();
    }

    private static  String readFromFile() throws URISyntaxException, IOException {
        var optionalKey = ConfigProvider.getConfig().getOptionalValue("mp.jwt.verify.publickey.location", String.class);
        if (optionalKey.isPresent()) {
            return readFromFile(optionalKey.get());
        }
        return null;
    }
}
