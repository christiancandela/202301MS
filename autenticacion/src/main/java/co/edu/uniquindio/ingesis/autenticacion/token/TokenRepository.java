package co.edu.uniquindio.ingesis.autenticacion.token;


import co.edu.uniquindio.ingesis.autenticacion.repository.StreamRepository;
/**
 * Interfaz que define para un token el conjunto de operaciones básicas de un repositorio para la gestión sus datos.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
public interface TokenRepository extends StreamRepository<Token> {
}
