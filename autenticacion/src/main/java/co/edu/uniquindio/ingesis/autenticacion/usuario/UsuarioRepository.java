package co.edu.uniquindio.ingesis.autenticacion.usuario;


import co.edu.uniquindio.ingesis.autenticacion.repository.StreamRepository;

import java.util.Optional;
/**
 * Interfaz que define para un usuario el conjunto de operaciones básicas de un repositorio para la gestión sus datos,
 * incorpora además de las funciones heredadas una operación de búsqueda basada en el username.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
public interface UsuarioRepository extends StreamRepository<Usuario> {
    /**
     * Permite buscar un usuario basado en su username.
     * @param username username del Usuario que se desea obtener
     * @return Optional con el Usuario a quien corresponde el username suministrado, en caso de no encontrar un Usuario
     * registrado con dicho username, se retorna un {@link Optional} vacío.
     */
    Optional<Usuario> findByUsername(String username);
}
