package co.edu.uniquindio.ingesis.autenticacion.token;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class DBTokenRepository implements TokenRepository{


    @PersistenceContext
    private EntityManager entityManager;

    public DBTokenRepository() {

    }

    @Override
    @Transactional
    public Collection<Token> getAll() {
        clear();
        return entityManager.createQuery(
          entityManager.getCriteriaBuilder().createQuery(Token.class)
        ).getResultList();
    }

    @Override
    @Transactional
    public Token save(Token token) {
        Objects.requireNonNull(token,"El token no puede ser nulo");
        var storageToken = findById(token.getToken());
        if( storageToken.isEmpty() ) {
            entityManager.persist(token);
        } else {
            token = entityManager.merge( storageToken.get() );
        }
        return token;
    }

    @Override
    @Transactional
    public Optional<Token> findById(String token) {
        clear();
        Objects.requireNonNull(token,"El token no puede ser nulo");
        return Optional.ofNullable( entityManager.find(Token.class,token) );
    }

    @Override
    @Transactional
    public void deleteById(String token) {
        findById(token).ifPresent(entityManager::remove);
    }

    private void clear(){
        LocalDateTime time = LocalDateTime.now();
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createCriteriaDelete(Token.class);

        entityManager.createQuery(
                query.where( criteriaBuilder.lessThan(query.from(Token.class).get("expirationDate"),time) )
        ).executeUpdate();
    }
}
