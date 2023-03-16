package co.edu.uniquindio.ingesis.microprofile.ejemplo.test.dtos;

import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class ErrorDTO {
    private final String error;

    @ConstructorProperties({"error"})
    public ErrorDTO(String error) {
        this.error = error;
    }
}
