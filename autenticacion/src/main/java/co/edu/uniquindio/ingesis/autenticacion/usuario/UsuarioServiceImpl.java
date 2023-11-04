package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.exception.LogicalException;
import co.edu.uniquindio.ingesis.autenticacion.token.Credential;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.PasswordHash;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {
    @Inject
    private UsuarioRepository repository;
    @Inject
    private PasswordHash passwordHash;

    @Override
    public Optional<Usuario> validate(Credential credential){
        var password = credential.password().toCharArray();
        Predicate<Usuario> claveValida = u->passwordHash.verify(password,u.password());
        var usuario = repository.findByUsername(credential.username());
        return usuario.stream().filter( claveValida ).findAny();
    }

    @Override
    public Optional<Usuario> register(Usuario usuario) {
        if( repository.findByUsername(usuario.username()).isPresent() ){
            throw new LogicalException("El usuario ya existe", Response.Status.CONFLICT);
        }
        var roles = Optional.ofNullable( usuario.roles() ).orElse(Set.of("user"));
        var nuevoUsuario = repository.save(
                Usuario.builder().id(usuario.id()).username(usuario.username())
                        .password(passwordHash.generate(usuario.password().toCharArray()))
                        .roles(roles).build());

        return Optional.ofNullable(nuevoUsuario);
    }

    @Override
    public void unregister(String username, String user){
        var usuario = get(username,user);
        unregister(usuario);
    }

    @Override
    public void unregister(String username){
        unregister(get(username));
    }

    @Override
    public void unregister(Usuario usuario) {
        repository.deleteById(usuario.id());
    }

    @Override
    public Optional<Usuario> update(Usuario usuario, String user) {
        checkAutorization(usuario.username(), user);
        return update(usuario);
    }

    @Override
    public Optional<Usuario> update(Usuario usuario) {
        unregister(usuario.username());
        return register(usuario);
    }

    @Override
    public Optional<Usuario> updatePassword(String username, String password) {
        var usuario = get(username);
        var usuarioActualizado = Usuario.builder()
                .id(usuario.id()).username(username).password(password).roles(usuario.roles()).build();
        return update(usuarioActualizado);
    }

    @Override
    public Optional<Usuario> updatePassword(String username, PasswordUpdateDTO passwordUpdateDTO, String user) {
        checkAutorization(username,user);
        var usuario = validate(new Credential(username, passwordUpdateDTO.password()));
        if(!passwordUpdateDTO.newPassword().equals(passwordUpdateDTO.verifyPassword())){
            throw new LogicalException("La nueva clave y su verificación no coinciden", Response.Status.BAD_REQUEST);
        }
        if( usuario.isEmpty() ){
            throw new LogicalException("Usuario o clave incorrecta", Response.Status.FORBIDDEN);
        }
        return updatePassword(username,passwordUpdateDTO.newPassword());
    }

    public Usuario get(String username,String user){
        Optional<Usuario> usuario = repository.findByUsername(username);
        checkAutorization(username, user);
        return usuario.orElseThrow(()->new LogicalException("Usuario no encontrado.", Response.Status.NOT_FOUND));
    }

    private static void checkAutorization(String username, String user) {
        if( user == null || user.isBlank() ){
            throw new LogicalException("Usuario no autorizado para realizar la operación.", Response.Status.UNAUTHORIZED);
        }
        if( !username.equalsIgnoreCase(user) ){
            throw new LogicalException("Usuario no posee permisos para realizar la operación.", Response.Status.FORBIDDEN);
        }
    }

    @Override
    public Usuario get(String username){
        Optional<Usuario> usuario = repository.findByUsername(username);
        return usuario.orElseThrow(()->new LogicalException("Usuario no encontrado.", Response.Status.NOT_FOUND));
    }

    @Override
    public Collection<Usuario> get(String username, String rol, String order){
        return repository.find(UsuarioUtil.INSTANCE.find(username,rol), UsuarioUtil.INSTANCE.order(order));
    }
}
