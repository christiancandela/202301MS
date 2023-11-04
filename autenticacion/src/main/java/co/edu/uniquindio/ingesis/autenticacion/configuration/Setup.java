package co.edu.uniquindio.ingesis.autenticacion.configuration;

import co.edu.uniquindio.ingesis.autenticacion.seguridad.Rol;
import co.edu.uniquindio.ingesis.autenticacion.usuario.Usuario;
import co.edu.uniquindio.ingesis.autenticacion.usuario.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.PasswordHash;

import java.util.Set;

@Startup
@Singleton
public class Setup {
    @Inject
    private UsuarioRepository repository;
    @Inject
    private PasswordHash passwordHash;

    @PostConstruct
    public void setup(){
        repository.save(Usuario.builder()
                .username(Rol.ADMIN_ROL)
                .password(passwordHash.generate(Rol.ADMIN_ROL.toCharArray()))
                .roles(Set.of(Rol.ADMIN_ROL))
                .build());
    }
}
