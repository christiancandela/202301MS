package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import org.eclipse.microprofile.config.ConfigProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


// https://github.com/jwtk/jjwt/tree/0.11.2
public abstract class TokenUtil {
    private static final String ROLES_KEY = "groups" ;
    private final long timeOfLife;
    private final String issuer;
    private final Key signatureKey;

    private final Key parseKey;

    private final SignatureAlgorithm algorithm;

    public TokenUtil(Key signatureKey, Key parseKey, SignatureAlgorithm algorithm) {
        this(ConfigProvider.getConfig().getOptionalValue("mp.jwt.timeoflife",Long.class).orElse(60L),
                ConfigProvider.getConfig().getOptionalValue("mp.jwt.verify.issuer",String.class).orElse("https://grid.uniquindio.edu.co"),
                signatureKey,parseKey,algorithm
                );
    }

    public TokenUtil(long timeOfLife, String issuer, Key signatureKey, Key parseKey, SignatureAlgorithm algorithm) {
        this.timeOfLife = timeOfLife;
        this.issuer = issuer;
        this.signatureKey = signatureKey;
        this.parseKey = parseKey;
        this.algorithm = algorithm;
    }

    public String create(String username, Set<String> roles) {
        return create(username, roles, getExpiration());
    }

    public String create(String username, Set<String> roles, Date expiration) {
        return create(username, roles, expiration, Collections.EMPTY_MAP);
    }

    public String create(String username, Set<String> roles, Date expiration, Map<String, Object> claims) {
        if (expiration == null) {
            expiration = getExpiration();
        }

        return Jwts.builder()
                .signWith(signatureKey, algorithm)
                .setClaims(claims)
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .claim(ROLES_KEY, String.join(",", roles))
                .setExpiration(expiration)
                .setId( UUID.randomUUID().toString() )
                .setIssuer(issuer)
                .setIssuedAt(Date.from(Instant.now()))
                .compact();
    }

    private Date getExpiration() {
        Instant now = Instant.now().plusSeconds(timeOfLife);
        return Date.from(now);
    }

//    public CredentialValidationResult parseToken(String token){
//        try {
////            Claims body = Jwts.parserBuilder().setSigningKey(PUBLIC_KEY).build().parseClaimsJws(token).getBody();
//            Claims body = buildJwtParserWithSigningKey().parseClaimsJws(token).getBody();
//            return new CredentialValidationResult(body.getSubject(), new HashSet<>(Arrays.asList(body.get(ROLES_KEY).toString().split(","))));
//        }catch (Exception e){
//            return INVALID_RESULT;
//        }
//    }

    public Map<String, ?> parseToken(String token) {
        Claims body = Jwts.parserBuilder().setSigningKey(parseKey).build().parseClaimsJws(token).getBody();
        return body.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected static Path getPathToKeyFile(String fileName) throws URISyntaxException {
        Path path;
        URL url = TokenUtil.class.getResource(fileName);
        if (url != null) {
            path = Paths.get(url.toURI());
        } else {
            path = Paths.get(fileName);
        }
        return path;
    }

    protected static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    protected static void saveKey(Key key, String fileName, String prefijo, String sufijo) {
        String secretString = prefijo + Encoders.BASE64.encode(key.getEncoded()) + sufijo;
        Path path = Paths.get(fileName);
        try {
            Files.write(path, secretString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static String readFromFile(String filePath) throws URISyntaxException, IOException {
        Path path = getPathToKeyFile(filePath);
        if (path.toFile().exists()) {
            return new String(Files.readAllBytes(path));
        }
        return null;
    }
}