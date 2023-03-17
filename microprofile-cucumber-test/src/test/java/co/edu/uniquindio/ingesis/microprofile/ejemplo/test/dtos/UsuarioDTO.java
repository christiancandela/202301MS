package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@Getter
@RequiredArgsConstructor(onConstructor_= @ConstructorProperties({"usuario", "clave"}))
@Builder
public class UsuarioDTO implements Serializable {
    private final String usuario;
    private final String clave;
}
