package co.edu.uniquindio.ingesis.serversocket;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class Servidor {
    private static final Logger LOGGER = Logger.getLogger(Servidor.class.getName());
    private final int puerto;

    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    public  void start() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)){
            LOGGER.info("Esperando una conexión...");
            while( true ) {
                ServerThread serverThread = new ServerThread( serverSocket.accept() );
                LOGGER.info("Un cliente se ha conectado...");
                serverThread.start();
            }
        } catch (IOException e) {
            LOGGER.warning("Error de entrada/salida."  + e.getMessage());
        }finally {
            LOGGER.info("Cerrando conexión...");
        }
 
    }
}