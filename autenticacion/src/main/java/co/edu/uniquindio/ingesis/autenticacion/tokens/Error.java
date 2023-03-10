package co.edu.uniquindio.ingesis.autenticacion.tokens;

public class Error {
    private String error;

    public Error(String error) {
        this.error = error;
    }

    public static Error of(String error) {
        return new Error(error);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
