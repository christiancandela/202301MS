package co.edu.uniquindio.ingesis.autenticacion.token;

import co.edu.uniquindio.ingesis.autenticacion.repository.MemoryAsbtractRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MemoryTokenRepository extends MemoryAsbtractRepository<Token> implements TokenRepository{


}
