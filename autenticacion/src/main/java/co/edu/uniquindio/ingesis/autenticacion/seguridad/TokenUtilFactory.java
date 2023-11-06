package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import org.eclipse.microprofile.config.ConfigProvider;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Factory de las clases utilitarias usadas para la generación de Tokens, actualmente con la posibilidad de usar RSA o SHA para su generación.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 *
 */
public enum TokenUtilFactory {
    /**
     * Representa el tipo de algoritmos RSA para la generación de Tokens
     */
    RSA(TokenRSAUtil::new, TokenRSAUtil::new),
    /**
     * Representación del tipo de algoritmos SHA para la generación de Tokens
     */
    SHA(TokenShaUtil::new, TokenShaUtil::new);

    /**
     * Instancia del constructor con dos parámetros de la clase utilitaria
     */
    private final BiFunction<Long,String,TokenUtil> constructor;
    /**
     * Instancia del constructor sin parámetros de la clase utilitaria.
     */
    private final Supplier<TokenUtil> voidConstructor;

    /**
     * Constructor del Factory
     * @param constructor Constructor con parámetros
     * @param voidConstructor Constructor sin parámetros
     */
    TokenUtilFactory(BiFunction<Long, String, TokenUtil> constructor, Supplier<TokenUtil> voidConstructor) {
        this.constructor = constructor;
        this.voidConstructor = voidConstructor;
    }

    /**
     * Implementación de Method Factory para la creación de una instancia de generación de tokens
     * @param timeOfLife Tiempo de vigencia del token
     * @param issuer Nombre de la entidad generadora del token
     * @return Clase utilitaria para la generación de tokens
     */
    public TokenUtil of(long timeOfLife, String issuer){
        return constructor.apply(timeOfLife,issuer);
    }

    /**
     * Implementación de Method Factory para la creación de una instancia de generación de tokens
     * @return Clase utilitaria para la generación de tokens
     */
    public TokenUtil of(){
        return voidConstructor.get();
    }

    /**
     * Método que permite obtener una instancia de la clase utilitaria para la generación de tokens
     * @return Clase utilitaria para la generación de tokens.
     */
    public static TokenUtilFactory getDefault(){
        return ConfigProvider.getConfig()
                .getOptionalValue("mp.jwt.algorithm", TokenUtilFactory.class)
                .orElse(RSA);
    }
}
