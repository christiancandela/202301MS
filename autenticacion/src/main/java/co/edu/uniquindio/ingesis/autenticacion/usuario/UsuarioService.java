package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.token.Credential;

import java.util.Collection;
import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> validate(Credential credential);

    Optional<Usuario> register(Usuario usuario);

    Optional<Usuario> update(Usuario usuario);

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
    Optional<Usuario> updatePassword(String username, PasswordUpdateDTO passwordUpdateDTO);
}
