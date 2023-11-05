package co.edu.uniquindio.ingesis.autenticacion.token;


import co.edu.uniquindio.ingesis.autenticacion.util.MessageSender;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * Clase encargada del envío de eventos cuando se gestionan los tokens. Actualmente, soporta dos eventos NEW y REMOVE.
 */
public enum TokenEvent {
    /**
     * Instancia para el envío de los eventos de nuevo token
     */
    NEW("TOKEN_ADD_QUEUE_NAME"),
    /**
     * Instancia para el envío de los eventos token removido
     */
    REMOVE("TOKEN_REMOVE_QUEUE_NAME");

    private final String exchangeName;
    private static final String URL = "MESSAGE_BUS";

    private final MessageSender messageSender;

    /**
     * Inicializa el canal para el envío de los eventos.
     * @param exchageName Canal para el envío de los eventos
     */
    TokenEvent(String exchageName) {
        this.exchangeName = exchageName;
        messageSender = new MessageSender(System.getenv(URL),System.getenv(exchangeName));
    }

    /**
     * Permite crear un evento basado en un Token y enviarlo, el canal por el que se envía el evento varía según la instancia de {@link TokenEvent} que lo cree.
     * @param token Token sobre el que se genera el evento.
     */
    public void createEvent(Token token){
        try{
                messageSender.initialize();
                JsonObject jsonObject = Json.createObjectBuilder()
                        .add("id",token.id())
                        .add("token",token.token())
                        .add("usuario",token.userName())
                        .add("vigencia",token.expirationDate().toString())
                        .add("roles", Json.createArrayBuilder(token.roles()))
                        .add("emisor",token.issuer())
                        .add("fechaEmision",token.issuerDate().toString())
                        .build();

               messageSender.send(jsonObject);
        } catch (Exception e){
            throw new WebApplicationException(
                    String.format("MESSAGE_BUS -> %s - %s - %s",System.getenv(URL),System.getenv(exchangeName),
                            e.getMessage()),Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
