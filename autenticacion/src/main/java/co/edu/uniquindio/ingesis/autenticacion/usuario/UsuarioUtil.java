package co.edu.uniquindio.ingesis.autenticacion.usuario;

import java.util.Comparator;
import java.util.function.Predicate;

public enum UsuarioUtil {
    INSTANCE;
    public Predicate<Usuario> findByUsername(String username){
        return usuario->usuario.username().equals(username);
    }

    public Predicate<Usuario> findByRol(String rol){
        return usuario->usuario.rols().contains(rol);
    }

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

    public Comparator<Usuario> order(String tipo){
        var comparetor = Comparator.comparing(Usuario::username);
        if( "DESC".equalsIgnoreCase(tipo) ){
            comparetor = comparetor.reversed();
        }
        return comparetor;
    }
}
