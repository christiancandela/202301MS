package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.exception.LogicalException;
import co.edu.uniquindio.ingesis.autenticacion.seguridad.TokenUtilFactory;
import co.edu.uniquindio.ingesis.autenticacion.usuario.UsuarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
/**
 * Implementación del servicio para la gestión de los tokens. Permite registrarlos y consultarlos.
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
@ApplicationScoped
public class TokenServiceImpl implements TokenService {
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private TokenRepository repository;

    /**
     * @see TokenService#generate(Credential)
     */
    @Override
    public Optional<Token> generate(Credential credential){
        var usuario = usuarioService.validate(credential);
        Token token = null;
        if(usuario.isPresent()){
            token = repository.save(TokenUtilFactory.getDefault().of().create(credential.username(), usuario.get().roles()));
            TokenEvent.NEW.createEvent(token);
        }
        return Optional.ofNullable(token);
    }

    /**
     * Remueve los tokens que han perdido vigencia.
     */
    private void clear(){
        LocalDateTime time = LocalDateTime.now();
        repository.find( e->time.isAfter(e.expirationDate()) ).stream().map(Token::id).forEach(repository::deleteById);
    }

    /**
     * @see TokenService#get(String)
     */
    @Override
    public Token get(String id){
        clear();
        Optional<Token> token = repository.findById(id);
        return token.orElseThrow(()->new LogicalException("Token no encontrado.", Response.Status.NOT_FOUND));
    }

    /**
     * @see TokenService#get()
     */
    @Override
    public Collection<Token> get(){
        clear();
        return repository.getAll();
    }
}
