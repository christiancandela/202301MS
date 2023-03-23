package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

// https://github.com/jwtk/jjwt/tree/0.11.2
public class TokenUtil {
    private static final String ROLES_KEY = "groups";
    private static final long VALID_TIME = TimeUnit.SECONDS.toMillis(60);
//    private static final Key MY_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final Key MY_KEY = loadKey();

    private static final String KEY_FILE_NAME = "mykey.pem";
    public static String create(String username, Set<String> roles,Date expiration){

        if( expiration == null ){
           expiration = getExpiration();
        }
        return Jwts.builder()
                .setSubject(username)
                .claim(ROLES_KEY, String.join(",", roles))
                .signWith(MY_KEY,SignatureAlgorithm.HS512)
                .setExpiration(expiration)
                .setIssuer("https://server.example.com")
                .compact();
    }

    public static String create(String username, Set<String> roles){
        return create(username,roles, getExpiration());
    }

    private static Date getExpiration() {
        return new Date(new Date().getTime()+VALID_TIME);
    }

    public static CredentialValidationResult parseToken(String token){
        try {
            Claims body = Jwts.parserBuilder().setSigningKey(MY_KEY).build().parseClaimsJws(token).getBody();
            return new CredentialValidationResult(body.getSubject(), new HashSet<>(Arrays.asList(body.get(ROLES_KEY).toString().split(","))));
        }catch (Exception e){
            return INVALID_RESULT;
        }
    }

    private static Key loadKey() {
        Key key;
        try{
            Path path = getPathToKeyFile();
            if( path.toFile().exists() ){
                key = Keys.hmacShaKeyFor( Files.readAllBytes(path) );
            } else{
                key = generateKey();
            }
        }catch (IOException | URISyntaxException exception){
            throw new RuntimeException("No se pudo cargar la llave",exception);
        }
        return key;
    }

    private static Path getPathToKeyFile() throws URISyntaxException {
        Path path;
        URL url = TokenUtil.class.getResource("/"+KEY_FILE_NAME);
        if( url != null ) {
            path = Paths.get(url.toURI());
        } else {
            path = Paths.get(KEY_FILE_NAME);
        }
        return path;
    }

    public static Key generateKey(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        saveKey(key);
        return key;
    }

    public static void saveKey(Key key) {
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        Path path = Paths.get("mykey.pem");
        try {
            Files.write(path, secretString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
