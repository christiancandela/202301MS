package co.edu.uniquindio.ingesis.autenticacion;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

/**
 *
 */
@LoginConfig(authMethod = "MP-JWT")
@ApplicationPath("/api")
@ApplicationScoped
@DeclareRoles({"user","admin"})
@SecurityScheme(securitySchemeName = "JWT",type = SecuritySchemeType.HTTP,scheme = "bearer",bearerFormat = "JWT")
public class AutenticacionRestApplication extends Application {
}
