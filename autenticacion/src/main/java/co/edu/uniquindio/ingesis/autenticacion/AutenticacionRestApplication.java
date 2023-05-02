package co.edu.uniquindio.ingesis.autenticacion;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;

/**
 *
 */
@LoginConfig(authMethod = "MP-JWT")
@ApplicationPath("/api")
@ApplicationScoped
@DeclareRoles({"user"})
public class AutenticacionRestApplication extends Application {
}
