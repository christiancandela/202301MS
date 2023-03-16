package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;

@Getter
@RequiredArgsConstructor(onConstructor_= @ConstructorProperties({"error"}))
public class ErrorDTO {
    private final String error;
}
