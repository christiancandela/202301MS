package co.edu.uniquindio.ingesis.autenticacion.token;

import java.util.Collection;
import java.util.Optional;

public interface TokenService {
    Optional<Token> generate(Credential credential);

    Token get(String id);

    Collection<Token> get();
}
