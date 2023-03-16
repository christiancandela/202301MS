package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(onConstructor_= @ConstructorProperties({"token", "vigencia","usuario"}))
public class TokenDTO {
    private final String token;
    private final LocalDateTime vigencia;
    private final String usuario;
}
