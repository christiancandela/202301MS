package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.repository.MemoryAsbtractRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Clase que implementa un {@link UsuarioRepository} con soporte de {@link Stream} almacenando los usuarios en memoria.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
@ApplicationScoped
public class MemoryUsuarioRepository extends MemoryAsbtractRepository<Usuario> implements UsuarioRepository {
    /**
     * @see UsuarioRepository#findByUsername(String)
     */
    @Override
    public Optional<Usuario> findByUsername(String username) {
        return findAny(usuario -> usuario.username().equalsIgnoreCase(username));
    }
}
