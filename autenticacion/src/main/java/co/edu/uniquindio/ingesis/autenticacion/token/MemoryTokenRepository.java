package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.repository.MemoryAsbtractRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.stream.Stream;

/**
 * Clase que implementa un {@link TokenRepository} con soporte de {@link Stream} almacenando los tokens en memoria.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
@ApplicationScoped
public class MemoryTokenRepository extends MemoryAsbtractRepository<Token> implements TokenRepository{


}
