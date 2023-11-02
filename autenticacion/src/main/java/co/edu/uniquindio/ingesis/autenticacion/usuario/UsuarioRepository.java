package co.edu.uniquindio.ingesis.autenticacion.usuario;


import co.edu.uniquindio.ingesis.autenticacion.repository.StreamRepository;

import java.util.Optional;

public interface UsuarioRepository extends StreamRepository<Usuario> {
    Optional<Usuario> findByUsername(String username);

}
