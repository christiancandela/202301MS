package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


// https://github.com/jwtk/jjwt/tree/0.11.2
public class TokenUtil2 {
    private static final String ROLES_KEY = "groups";
    private static final long VALID_TIME = TimeUnit.SECONDS.toMillis(60);
    //    private static final Key MY_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//    private static final Key PUBLIC_KEY = null//;loadKey();
    private static final Key PUBLIC_KEY = loadKey();

    private static final String KEY_FILE_NAME = "public.pem";
    private static final Key PRIVATE_KEY = loadPrivateKey();

    private static final String PRIVATE_KEY_FILE_NAME = "private.pem";
    private static final String PUBLIC_KEY_FILE_NAME = "public.pem";

    private static final String PRIVATE_KEY_STRING = """
-----BEGIN PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDzHIIxByU39NDR
RIFPPQyWDlDEnvULIsEaNvdMaXx+g0LymANyrgvExlBKb0nhUwqZjyycciwt1Iif
1CyXeuRI66rr0QhfotvVVZt+ZQCo18LMdGN8jTbAhRR6FIUSPNGZshSkLk15zcHP
wXcys2/uuXxVxLb8hU7bQK7eVfNc6bDW7U7umkBJWkGLqdHbwWOf4tp0zaZUdPKr
DKKOswXyLTQhOf7iiXO4+MVDGML2VyjnxJ2ew/XP0jHDeOdqdtUjvBwvvjLniBRD
nYaiUIL5cxlZF7P8JD8JUzCthfMfG320WnwAMtLVDpu5WyeALa307hboMQNWlkyU
D1uvxldHAgMBAAECggEBAOBsOdcupHICYt48Ii7WyY6gSnkn0EfmXmJnyWTVoCJd
4+h04gH0n1KYS7hbAdWqxgc5v1Hwp83q+/Cnej6gKKsUOuWUQumeOte2PmOws6op
+16oNsrcxWV1tJB57toGIOuEFD/3qne9tKOoEfRQ5wx2WEwI229DVKdcD9GZgJ4M
JA7N/0vP+Tjd2K0GuzH3J6LJ6Cyqsq2RBOeI6A+JDX4GGsoWIXhE3sBLDvZ162dW
sVHnu+qAeZ0E6H/zWzuHq+5+EPgj1e73eKjp5aXjQ4SdQjf95HTZN0vC0x+WZWOg
6mDucum6pSE4VOywXile2ELdUvOg+Pc6FKZeDtIq35kCgYEA/mwPUhPjboM04yNA
QUOzTa1Mqcv9xf/FSGx+/+Lam6N2rj5tPe+5OUzucTQ0zfrrZLyZQObwMI0VIZSd
Z76RTzhkdRQIybTAUibrl9DsRW4BVKDGkXpDMd1jQ1vLtP0tIZi0QTjP7mqaufjH
Jvnxe1XI7jmgQjzv2FYzJ5yn9IsCgYEA9J59q6Lf3EB3BXEPTWMA6Ag4TEwHbPZ3
GL85iV18h1bcOv8ZqrgHPupIcOs6Y7+TJmqt6qhdH61AQgWeOedE0GE/i0omX8NU
e1Sg6Vj4/fEhxeqbh9tDDlgGQctvauoE8od0kdYihg30dGCDkmWoMNZTSXjHjKRK
QUae3PgEc7UCgYBcDH6ShI2RazkUQKm7syHJJb0J5bsACuS2qkJclBfhnYePzRg7
A2NxPss5+9hq+bB2tuF/oM4f0rtldd5pLYJnNhDqZwAy8glu75PnTnhdOkqS8sRE
2AY9oUqLkUenSL4uXJBC1KouKTWnuUFccauTotrajdDUhjWEgNNrLnt5twKBgA71
wmnExDwMFTtX9r+c1mlaV1cTL0ESsaXpy/MCrKL6RO22vDMcnhTS7ys4t5FyHeWz
kH9RXKCbT9q5zTttUWANEn3KQx6IK5p/Snf64P9mx9H6zxg36Jnv+DH95wp61WKf
thlVJzdbkx+q52EEpoyWDvHnWQzY31Gup/iLknohAoGAJojmqhkcKycQbumr/SC8
U0BpNveetj71wSvFpOzO50HSph5Mxj8GzaER5HdyNVCtWSTd4cWlx/oF/X6IV2WL
ewADOqSN9U/JC/uUmKR9jobpdoT317DWTzSU/1zG+RwJPPxsFF0fqp8oQnMwyT0K
P4gNS184F5ffuX++G9zaOiE=
-----END PRIVATE KEY-----
            """;

    private static final String PUBLIC_KEY_STRING = """
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8xyCMQclN/TQ0USBTz0M
lg5QxJ71CyLBGjb3TGl8foNC8pgDcq4LxMZQSm9J4VMKmY8snHIsLdSIn9Qsl3rk
SOuq69EIX6Lb1VWbfmUAqNfCzHRjfI02wIUUehSFEjzRmbIUpC5Nec3Bz8F3MrNv
7rl8VcS2/IVO20Cu3lXzXOmw1u1O7ppASVpBi6nR28Fjn+LadM2mVHTyqwyijrMF
8i00ITn+4olzuPjFQxjC9lco58SdnsP1z9Ixw3jnanbVI7wcL74y54gUQ52GolCC
+XMZWRez/CQ/CVMwrYXzHxt9tFp8ADLS1Q6buVsngC2t9O4W6DEDVpZMlA9br8ZX
RwIDAQAB
-----END PUBLIC KEY-----
            """;

    public static String create(String username, Set<String> roles,Date expiration){

        if( expiration == null ){
            expiration = getExpiration();
        }
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(username)
                .claim(ROLES_KEY, String.join(",", roles))
                .signWith(PRIVATE_KEY,SignatureAlgorithm.RS256)
                .setExpiration(expiration)
                .setId( UUID.randomUUID().toString() )
                .setIssuedAt(Date.from(Instant.now()))
                .setIssuer("https://server.example.com")
                .compact();
    }

    public static void main(String[] args) {
        var token = TokenUtil2.create("pedro", Set.of("user"));
        System.out.println(token);
        System.out.println(token.length());
        System.out.println( parseToken(token) );


    }

    public static String create(String username, Set<String> roles){
        return create(username,roles, getExpiration());
    }

    public static Claims parseToken(String token){
        Claims body = Jwts.parserBuilder().setSigningKey(PUBLIC_KEY).build().parseClaimsJws(token).getBody();
        return body;
    }

    private static Date getExpiration() {
        return new Date(new Date().getTime()+VALID_TIME);
    }


    private static Key loadKey() {
        Key key;
        try{
            var byteKey = Base64.getDecoder().decode(removeBeginEnd( PUBLIC_KEY_STRING ) );
            var encodedKey = new X509EncodedKeySpec(byteKey);
            key = KeyFactory.getInstance("RSA").generatePublic(encodedKey);
        }catch (NoSuchAlgorithmException | InvalidKeySpecException exception){
            throw new RuntimeException("No se pudo cargar la llave",exception);
        }
        return key;
    }

    private static Key loadKeyOld() {
        Key key;
        try{
            Path path = getPathToKeyFile(PUBLIC_KEY_FILE_NAME);
            if( path.toFile().exists() ){
                var byteKey = Base64.getDecoder().decode(removeBeginEnd( new String( Files.readAllBytes(path) ) ) );
                var encodedKey = new X509EncodedKeySpec(byteKey);
                key = KeyFactory.getInstance("RSA").generatePublic(encodedKey);
            } else{
                key = generateKey();
            }
        }catch (IOException | URISyntaxException | NoSuchAlgorithmException | InvalidKeySpecException exception){
            throw new RuntimeException("No se pudo cargar la llave",exception);
        }
        return key;
    }
    private static Key loadPrivateKey() {
        Key key;
        try{
                var byteKey = Base64.getDecoder().decode(removeBeginEnd( PRIVATE_KEY_STRING ) );
                var encodedKey = new PKCS8EncodedKeySpec(byteKey);
                key = KeyFactory.getInstance("RSA").generatePrivate(encodedKey);
        }catch (NoSuchAlgorithmException | InvalidKeySpecException exception){
            throw new RuntimeException("No se pudo cargar la llave",exception);
        }
        return key;
    }

    private static Key loadPrivateKey2() {
        Key key;
        try{
            Path path = getPathToKeyFile(PRIVATE_KEY_FILE_NAME);
            if( path.toFile().exists() ){
                Files.readAllBytes(path);
//                var byteKey = Base64.getDecoder().decode(removeBeginEnd( new String( Files.readAllBytes(path) ) ) );
                var byteKey = Base64.getDecoder().decode(removeBeginEnd( PRIVATE_KEY_STRING ) );
                var encodedKey = new PKCS8EncodedKeySpec(byteKey);
                key = KeyFactory.getInstance("RSA").generatePrivate(encodedKey);
            } else{
                key = generateKeys();
            }
        }catch (IOException | URISyntaxException | NoSuchAlgorithmException | InvalidKeySpecException exception){
            throw new RuntimeException("No se pudo cargar la llave",exception);
        }
        return key;
    }
    private static Key generateKeys() {
        var keyPar = Keys.keyPairFor(SignatureAlgorithm.RS256);
        Key key = keyPar.getPrivate();
        saveKey(key,PRIVATE_KEY_FILE_NAME);
        saveKey(keyPar.getPublic(),PUBLIC_KEY_FILE_NAME);
        return key;
    }

    private static Path getPathToKeyFile() throws URISyntaxException {
        return getPathToKeyFile(KEY_FILE_NAME);
    }

    private static Path getPathToKeyFile(String fileName) throws URISyntaxException {
        Path path;
        URL url = TokenUtil2.class.getResource("/META-INF/"+fileName);
        if( url != null ) {
            path = Paths.get(url.toURI());
        } else {
            path = Paths.get(fileName);
        }
        return path;
    }

    public static Key generateKey(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        saveKey(key);
        return key;
    }

    public static Key generateKey(String fileName){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        saveKey(key,fileName);
        return key;
    }

    private static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    public static void saveKey(Key key) {
        saveKey(key,KEY_FILE_NAME);
    }

    public static void saveKey(Key key,String fileName) {
        String secretString = "-----BEGIN PUBLIC KEY-----\n"+Encoders.BASE64.encode(key.getEncoded())+"\n-----END PUBLIC KEY-----";
        Path path = Paths.get(fileName);
        try {
            Files.write(path, secretString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
