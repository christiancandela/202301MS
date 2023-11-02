package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.token.Credential;

import java.util.Collection;
import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> validate(Credential credential);

    Optional<Usuario> register(Usuario usuario);

    void unregister(String username, String user);

    void unregister(String username);

    void unregister(Usuario usuario);

    Usuario get(String username);

    Collection<Usuario> get(String username, String rol, String order);
}
