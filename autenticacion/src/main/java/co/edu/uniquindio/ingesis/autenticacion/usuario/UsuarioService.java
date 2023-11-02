package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.token.Credential;
import co.edu.uniquindio.ingesis.autenticacion.token.Token;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.PasswordHash;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.Optional;
import java.util.function.Predicate;

@ApplicationScoped
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    @Inject
    private PasswordHash passwordHash;

    public Optional<Usuario> validate(Credential credential){
        var password = credential.password().toCharArray();
        Predicate<Usuario> claveValida = u->passwordHash.verify(password,u.password());
        var usuario = usuarioRepository.findByUsername(credential.username());
        return usuario.stream().filter( claveValida ).findAny();
    }


}
