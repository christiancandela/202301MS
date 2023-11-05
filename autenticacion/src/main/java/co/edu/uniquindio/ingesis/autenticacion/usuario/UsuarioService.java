package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.exception.LogicalException;
import co.edu.uniquindio.ingesis.autenticacion.token.Credential;

import java.util.Collection;
import java.util.Optional;
/**
 * Interfaz que define las operaciones del servicio para la gestión de los usuarios.
 * Permite registrarlos, obtenerlos, actualizarlos y borrarlos.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
public interface UsuarioService {
    /**
     * Permite verificar las credenciales de un Usuario, en caso de que sean válidas retorna un Optional con el Usuario al que corresponden las credenciales.
     * @param credential Credenciales (username y password) a ser validadas
     * @return Optional con el Usuario al que corresponden las credenciales, en caso de no ser válidas se retorna un Optional vacío.
     */
    Optional<Usuario> validate(Credential credential);

    /**
     * Permite registrar un usuario en el sistema
     * @param usuario Usiario a ser registrado
     * @return Optional con el {@link Usuario} registrado, en caso de no poder registrarlo se retorna un Optional vacío.
     * @throws LogicalException En caso de que ya exista un usuario con el nombre de usuario proporcionado se genera una {@link LogicalException}.
     */
    Optional<Usuario> register(Usuario usuario);

    /**
     * Permite actualizar los datos de un Usuario.
     * @param usuario Usuario a ser actualizado
     * @return Optional con el Usuario actualizado, en caso de no poder realizar la actualización se retorna un Optional vacío.
     * @throws LogicalException En caso de que no haya un usuario autenticado o el usuario autenticado cuente
     * con permisos para realizar la operación, o cuando no se encuentra el Usuario que se desea actualizar.
     */
    Optional<Usuario> update(Usuario usuario);

    /**
     * Permite eliminar un usuario
     *
     * @param username Nombre de usuario del {@link Usuario} que se desea eliminar
     * @throws LogicalException En caso de que no haya un usuario autenticado o el usuario autenticado cuente
     * con permisos para realizar la operación, o cuando no se encuentra un Usuario al que pertenezca el username dado.
     */
    void unregister(String username);
    /**
     * Permite obtener un usuario usando su username para buscarlo,
     *
     * @param username Nombre de usuario del usuario que se desea obtener.
     * @return {@link Usuario} cuyo nombre de usuario coincida con el nombre de usuario dado.
     * @throws co.edu.uniquindio.ingesis.autenticacion.exception.LogicalException En caso de no encontrarlo se lanzará una LogicalException indicando que no se encontró el usuario.
     */
    Usuario get(String username);
    /**
     * Permite obtener una lista de los usuarios usando los filtros de username y rol si estos son diferentes de null,
     * en caso de que ambos sean omitidos, se retornarán todos los usuarios registrados en el sistema ordenados
     * por su nombre de usuario en forma ascendente, si se desea obtener el listado en forma descendente se debe asignar a order DESC
     * @param username Fracción del nombre de usuario que se desea buscar.
     * @param rol Rol que deben tener todos los usuarios buscados.
     * @param order Orden en que se desea obtener la lista (DESC - descendente) o cualquier otro valor para ascendente
     * @return La lista de {@link Usuario}s que cumple con los criterios dados
     */
    Collection<Usuario> get(String username, String rol, String order);

    /**
     * Permite actualizar la clave de un usuario, para lo cual se proporciona la clave actual, la nueva clave y una confirmación de la nueva clave.
     * @param username Nombre de usuario del {@link Usuario} al que se desea actualizar la clave.
     * @param passwordUpdateDTO {@link PasswordUpdateDTO} que contiene la clave actual, la nueva clave y una confirmación de la nueva clave.
     * @return Optional con el Usuario actualizado, en caso de no poder realizar la actualización se retorna un Optional vacío.
     * @throws LogicalException En caso de que no haya un usuario autenticado o el usuario autenticado cuente
     * con permisos para realizar la operación, o cuando no se encuentra un Usuario al que pertenezca el username dado.
     */
    Optional<Usuario> updatePassword(String username, PasswordUpdateDTO passwordUpdateDTO);
}
