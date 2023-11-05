package co.edu.uniquindio.ingesis.autenticacion.usuario;

import java.util.Comparator;
import java.util.function.Predicate;
/**
 * Clase utilitaria que define las posibles búsquedas a realizar sobre un conjunto de usuarios, basado en sus atributos nombre de usuario y rol
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */

public enum UsuarioUtil {
    /**
     * Instancia única de la clase utilitaria
     */
    INSTANCE;

    /**
     * Condición que especifica la búsqueda por nombre de usuario, donde los usuarios deben contener el username dado en su nombre de usuario.
     * @param username Nombre de usuario buscado
     * @return Predicate construido como la coincidencia del username búscado con el username del {@link Usuario}
     */
    public Predicate<Usuario> findByUsername(String username){
        return usuario->usuario.username().contains(username);
    }
    /**
     * Condición que especifica la búsqueda de los usuarios que contengan un rol específico
     * @param rol Rol de usuario a ser usado como condición de búsqueda
     * @return Predicate construido como la existencia de rol entre el conjunto de roles del {@link Usuario}
     */
    public Predicate<Usuario> findByRol(String rol){
        return usuario->usuario.roles().contains(rol);
    }

    /**
     * Permite combinar los {@link Predicate} de findByUsername y findByRol en uno solo dependiendo de los parámetros enviados.
     * @param username Nombre de usuario buscado
     * @param rol Rol de usuario a ser usado como condición de búsqueda
     * @return Predicate que combina la búsqueda por rol y por username.
     */
    public Predicate<Usuario> find(String username,String rol){
        Predicate<Usuario> predicate = usuario -> true;
        if( username != null && !username.isBlank()  ){
            predicate = findByUsername(username);
        }
        if( rol != null && !rol.isBlank() ){
            predicate = predicate.and(findByRol(rol));
        }
        return predicate;
    }

    /**
     * Retorna un {@link Comparator} a ser usando para ordenar los resultados de las búsquedas.
     * El comparetor se basa en el nombre de usuario.
     * @param tipo DESC o ASC, si se especifica DESC se ordena por alfabéticamente en orden descendente, en caso contrario se usa ordenamiento ascendente.
     * @return Comparetor a usar para el ordenamiento de las búsquedas.
     */
    public Comparator<Usuario> order(String tipo){
        var comparetor = Comparator.comparing(Usuario::username);
        if( "DESC".equalsIgnoreCase(tipo) ){
            comparetor = comparetor.reversed();
        }
        return comparetor;
    }
}
