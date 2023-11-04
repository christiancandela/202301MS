package co.edu.uniquindio.ingesis.autenticacion;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

/**
 * Clase de configuración inicial de la aplicación
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 *
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
@LoginConfig(authMethod = "MP-JWT")
@ApplicationPath("/api")
@ApplicationScoped
@DeclareRoles({"user","admin"})
@SecurityScheme(securitySchemeName = "JWT",type = SecuritySchemeType.HTTP,scheme = "bearer",bearerFormat = "JWT")
@OpenAPIDefinition(info=@Info(title = "API de autenticación",version = "1.0",description = "Microservicio de autenticación, el cual permite el registro de usuarios y la generación token usando las credenciales de los usuarios. Usado para ilustrar el tema de seguridad basada en el uso de JWT"))
public class AutenticacionRestApplication extends Application {
}
