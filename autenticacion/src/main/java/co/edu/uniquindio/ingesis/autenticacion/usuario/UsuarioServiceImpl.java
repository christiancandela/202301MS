package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.exception.LogicalException;
import co.edu.uniquindio.ingesis.autenticacion.seguridad.Rol;
import co.edu.uniquindio.ingesis.autenticacion.token.Credential;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.PasswordHash;
import jakarta.ws.rs.core.Response;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
/**
 * Implementación del servicio para la gestión de los usuarios. Permite registrarlos, obtenerlos, actualizarlos y borrarlos.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {
    @Inject
    private UsuarioRepository repository;
    @Inject
    private PasswordHash passwordHash;
    @Inject
    private Principal principal;
    @Inject
    private SecurityContext securityContext;

    /**
     * @see UsuarioService#validate(Credential)
     */
    @Override
    public Optional<Usuario> validate(Credential credential){
        var password = credential.password().toCharArray();
        Predicate<Usuario> claveValida = u->passwordHash.verify(password,u.password());
        var usuario = repository.findByUsername(credential.username());
        return usuario.stream().filter( claveValida ).findAny();
    }

    /**
     * @see UsuarioService#register(Usuario)
     */
    @Override
    public Optional<Usuario> register(Usuario usuario) {
        if( repository.findByUsername(usuario.username()).isPresent() ){
            throw new LogicalException("El usuario ya existe", Response.Status.CONFLICT);
        }
        var roles = Optional.ofNullable( usuario.roles() ).orElse(Set.of("user"));
        if(isUser()){
            roles = Set.of("user");
        }
        var nuevoUsuario = repository.save(
                Usuario.builder().id(usuario.id()).username(usuario.username())
                        .password(passwordHash.generate(usuario.password().toCharArray()))
                        .roles(roles).build());

        return Optional.ofNullable(nuevoUsuario);
    }

    /**
     * @see UsuarioService#unregister(String)
     */
    @Override
    public void unregister(String username){
        repository.deleteById(get(username).id());
    }

    /**
     * @see UsuarioService#update(Usuario)
     */
    @Override
    public Optional<Usuario> update(Usuario usuario) {
        unregister(usuario.username());
        return register(usuario);
    }

    /**
     * Permite actualizar la clave de un usuario.
     *
     * @param username nombre de usuario al que se desea cambiar la clave.
     * @param password nueva clave a ser asignada.
     * @return Optional que contiene él {@link Usuario} al que se le modificó la clave, en caso de no haber podido
     * realizar la operación, retornará un Optional vacío.
     * @throws LogicalException En caso de que no haya un usuario autenticado o el usuario autenticado cuente
     * con permisos para realizar la operación, o cuando no se encuentra un Usuario al que pertenezca el username dado.
     */
    private Optional<Usuario> updatePassword(String username, String password) {
        var usuario = get(username);
        var usuarioActualizado = Usuario.builder()
                .id(usuario.id()).username(username).password(password).roles(usuario.roles()).build();
        return update(usuarioActualizado);
    }

    /**
     * @see UsuarioService#updatePassword(String, PasswordUpdateDTO)
     */
    @Override
    public Optional<Usuario> updatePassword(String username, PasswordUpdateDTO passwordUpdateDTO) {
        if(isUser()) {
            var usuario = validate(new Credential(username, passwordUpdateDTO.password()));
            if (!passwordUpdateDTO.newPassword().equals(passwordUpdateDTO.verifyPassword())) {
                throw new LogicalException("La nueva clave y su verificación no coinciden", Response.Status.BAD_REQUEST);
            }
            if (usuario.isEmpty()) {
                throw new LogicalException("Usuario o clave incorrecta", Response.Status.FORBIDDEN);
            }
        }
        return updatePassword(username,passwordUpdateDTO.newPassword());
    }

    /**
     * Verifica que el usuario autenticado pueda realizar operaciones sobre el usuario al que pertenece el username.
     * Esto es, si es un admin o si es el mismo usuario sobre el que se está realizando la operación.
     * En caso de no cumplir ninguno de los requisitos se genera una excepción.
     * @param username Username del usuario que se ve afectado por la operación.
     *
     * @throws LogicalException En caso de que no haya un usuario autenticado o el usuario autenticado no sea un
     * administrador y no sea a el a quien pertenece el username dado.
     */
    private void checkAutorization(String username) {
        if (isUser()) {
            if( principal == null || principal.getName() == null || principal.getName().isBlank() ){
                throw new LogicalException("Usuario no autorizado para realizar la operación.", Response.Status.UNAUTHORIZED);
            }
            if( !username.equalsIgnoreCase(principal.getName()) ){
                throw new LogicalException("Usuario no posee permisos para realizar la operación.", Response.Status.FORBIDDEN);
            }
        }
    }

    /**
     * @see UsuarioService#get(String)
     */
    @Override
    public Usuario get(String username){
        checkAutorization(username);
        Optional<Usuario> usuario = repository.findByUsername(username);
        return usuario.orElseThrow(()->new LogicalException("Usuario no encontrado.", Response.Status.NOT_FOUND));
    }

    /**
     * @see UsuarioService#get(String, String, String)
     */
    @Override
    public Collection<Usuario> get(String username, String rol, String order){
        return repository.find(UsuarioUtil.INSTANCE.find(username,rol), UsuarioUtil.INSTANCE.order(order));
    }

    /**
     * Permite determinar si el usuario que está interactuando con el sistema es solo un usuario.
     * @return false en caso de que el usuario posea el rol de administrador, en caso contrario retornará true
     */
    private boolean isUser(){
        return !securityContext.isCallerInRole(Rol.ADMIN_ROL);
    }
}
