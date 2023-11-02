package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.exception.LogicalException;
import co.edu.uniquindio.ingesis.autenticacion.seguridad.TokenUtilFactory;
import co.edu.uniquindio.ingesis.autenticacion.usuario.UsuarioRepository;
import co.edu.uniquindio.ingesis.autenticacion.usuario.UsuarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class TokenService {
    private UsuarioService usuarioService;
    private TokenRepository repository;

    public Optional<Token> generate(Credential credential){
        var usuario = usuarioService.validate(credential);
        Token token = null;
        if(usuario.isPresent()){
            token = repository.save(TokenUtilFactory.getDefault().of().create(credential.username(), usuario.get().rols()));
            TokenEvent.NEW.createEvent(token);
        }
        return Optional.ofNullable(token);
    }

    public void invalidate(String id,String username){
        var token = get(id);
        if( username == null || username.isBlank() ){
            throw new WebApplicationException("Usuario no autorizado para realizar la operación.", Response.Status.UNAUTHORIZED);
        }
        if( !token.userName().equalsIgnoreCase(username) ){
            throw new LogicalException("Usuario no posee permisos para realizar la operación.", Response.Status.FORBIDDEN);
        }
        invalidate(token);
    }

    public void invalidate(String id){
        var token = get(id);
        invalidate(token);
    }

    private void invalidate(Token token) {
        repository.deleteById(token.id());
        TokenEvent.REMOVE.createEvent(token);
    }

    public void clear(){
        LocalDateTime time = LocalDateTime.now();
        repository.find( e->time.isAfter(e.expirationDate()) ).stream().map(Token::id).forEach(repository::deleteById);
    }

    private Token get(String id){
        Optional<Token> token = repository.findById(id);
        return token.orElseThrow(()->new LogicalException("Token no encontrado.", Response.Status.NOT_FOUND));
    }
}
