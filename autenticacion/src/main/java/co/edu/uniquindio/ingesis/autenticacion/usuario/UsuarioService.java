package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.token.Credential;

import java.util.Collection;
import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> validate(Credential credential);

    Optional<Usuario> register(Usuario usuario);

    Optional<Usuario> update(Usuario usuario, String user);

    Optional<Usuario> update(Usuario usuario);

    void unregister(String username, String user);

    void unregister(String username);

    void unregister(Usuario usuario);

    Usuario get(String username,String user);
    Usuario get(String username);

    Collection<Usuario> get(String username, String rol, String order);

    Optional<Usuario> updatePassword(String username, String password);

    Optional<Usuario> updatePassword(String username, PasswordUpdateDTO passwordUpdateDTO, String user);
}
