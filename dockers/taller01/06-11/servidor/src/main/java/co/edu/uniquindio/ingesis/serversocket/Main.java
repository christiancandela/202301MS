package co.edu.uniquindio.ingesis.serversocket;

public class Main {
    private static int PUERTO = 80;

    public static void main(String args[]) {
        Servidor servidor = new Servidor(PUERTO);
        servidor.start();
    }
}
