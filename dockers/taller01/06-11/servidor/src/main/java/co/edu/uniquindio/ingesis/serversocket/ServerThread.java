package co.edu.uniquindio.ingesis.serversocket;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerThread extends Thread{
    private final Socket socket;
    private static final Logger LOGGER = Logger.getLogger(ServerThread.class.getName());

    public ServerThread(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        super.run();
        String root = getEnv("ROOT","root");
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(
                socket.getInputStream())) ; PrintStream salida = new PrintStream(socket.getOutputStream()) ){
            String mensajeRecibido = entrada.readLine();
            LOGGER.info("Se recibió: "+mensajeRecibido);
            int indice = mensajeRecibido.indexOf("CLIENTE ") ;
            String respuesta;
            if( indice != 0 ){
                respuesta="ERROR: Mensaje no valido...";
            } else {
                String cliente = mensajeRecibido.substring(8);
                if(root.equals(cliente)){
                    cliente = "ADMINISTRADOR";
                }
                respuesta ="HOLA "+cliente;
            }
            LOGGER.info("Se respondió: "+respuesta);
            salida.println(respuesta);
            LOGGER.info("Cerrando conexión...");
        } catch (IOException e) {
            LOGGER.warning("Error de entrada/salida."  + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.warning("Error al cerrar el socket del cliente."+e.getMessage());
            }
        }
    }

    private static String getEnv(String variable,String defaultValue){
        String host = System.getenv(variable);
        if( host == null ){
            host = defaultValue;
        }
        return host;
    }
}
