package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class MemoryTokenRepository implements TokenRepository{

    private Map<String,Token> tokens;

    public MemoryTokenRepository() {
        tokens = new HashMap<>();
    }

    @Override
    public Collection<Token> getAll() {
        clear();
        return tokens.values();
    }

    @Override
    public Token save(Token token) {
        Objects.requireNonNull(token,"El token no puede ser nulo");
        tokens.put(token.getToken(),token);
        return token;
    }

    @Override
    public Optional<Token> findById(String token) {
        clear();
        return Optional.ofNullable( tokens.get(token) );
    }

    @Override
    public void deleteById(String token) {
        tokens.remove(token);
    }

    private void clear(){
        LocalDateTime time = LocalDateTime.now();
        tokens = tokens.entrySet().stream()
                .filter( e->time.isBefore(e.getValue().getExpirationDate()) )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
