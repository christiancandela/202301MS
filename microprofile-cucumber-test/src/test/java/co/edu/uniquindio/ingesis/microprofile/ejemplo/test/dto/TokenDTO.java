package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(onConstructor_={@ConstructorProperties({"token","usuario","vigencia"})} )
public class TokenDTO {
    private final String token;
    private final String usuario;
    private final LocalDateTime vigencia;

}
