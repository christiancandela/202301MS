package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.exception.LogicalException;

import java.util.Collection;
import java.util.Optional;
/**
 * Interfaz que define las operaciones del servicio para la gestión de los tokens.
 * Permite registrarlos, obtenerlos.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
public interface TokenService {
    /**
     * Permite generar un token a partir de las credenciales del dicho Usuario. Las credenciales deben ser válidas.
     * @param credential Credenciales del usuario para el que se desea generar el {@link Token}
     * @return Optional con el {@link Token} generado, o un Optional vacío en caso de que no se haya podido generar el {@link Token}.
     */
    Optional<Token> generate(Credential credential);

    /**
     * Permite obtener los datos de un token dado su id.
     * @param id Identificador del {@link Token} que se desea obtener.
     * @return Token que corresponde el id.
     * @throws LogicalException en caso de que no se encuentre un token con el id dado.
     */
    Token get(String id);

    /**
     * Listado de Tokens registrados que aún están vigentes.
     * @return Collection con los tokens registrados que aún están vigentes. En caso de no haber ningún token registrado se retorna un Collection vacío.
     */
    Collection<Token> get();
}
