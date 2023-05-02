package co.edu.uniquindio.ingesis.autenticacion.seguridad;

import org.eclipse.microprofile.config.ConfigProvider;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public enum TokenUtilFactory {
    RSA(TokenRSAUtil::new, TokenRSAUtil::new),SHA(TokenShaUtil::new, TokenShaUtil::new);

    private final BiFunction<Long,String,TokenUtil> constructor;
    private final Supplier<TokenUtil> voidConstructor;

    TokenUtilFactory(BiFunction<Long, String, TokenUtil> constructor, Supplier<TokenUtil> voidConstructor) {
        this.constructor = constructor;
        this.voidConstructor = voidConstructor;
    }

    public TokenUtil of(long timeOfLife, String issuer){
        return constructor.apply(timeOfLife,issuer);
    }

    public TokenUtil of(){
        return voidConstructor.get();
    }

    public static TokenUtilFactory getDefault(){
        return ConfigProvider.getConfig()
                .getOptionalValue("mp.jwt.algorithm", TokenUtilFactory.class)
                .orElse(RSA);
    }
}
