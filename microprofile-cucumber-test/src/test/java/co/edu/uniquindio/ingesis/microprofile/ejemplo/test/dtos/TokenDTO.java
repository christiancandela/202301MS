package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(onConstructor_= @ConstructorProperties({"usuario", "vigencia","token"}))
public class TokenDTO {
    private String usuario;
    private LocalDateTime vigencia;
    private String token;
}
