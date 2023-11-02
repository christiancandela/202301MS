package co.edu.uniquindio.ingesis.autenticacion.usuario;

import co.edu.uniquindio.ingesis.autenticacion.repository.MemoryAsbtractRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class MemoryUsuarioRepository extends MemoryAsbtractRepository<Usuario> implements UsuarioRepository {
    @Override
    public Optional<Usuario> findByUsername(String username) {
        return findAny(usuario -> usuario.username().equalsIgnoreCase(username));
    }
}
