package co.edu.uniquindio.ingesis.autenticacion.token;

import java.util.Collection;
import java.util.Optional;

public interface TokenService {
    Optional<Token> generate(Credential credential);

    void invalidate(String id, String username);

    void invalidate(String id);

    void invalidate(Token token);

    void clear();

    Token get(String id);

    Collection<Token> get();
}
