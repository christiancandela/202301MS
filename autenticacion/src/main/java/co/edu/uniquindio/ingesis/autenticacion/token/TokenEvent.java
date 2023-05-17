package co.edu.uniquindio.ingesis.autenticacion.token;


import co.edu.uniquindio.ingesis.autenticacion.util.MessageSender;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public enum TokenEvent {
    NEW("TOKEN_ADD_QUEUE_NAME"),
    REMOVE("TOKEN_REMOVE_QUEUE_NAME");

    private final String exchangeName;
    private static final String URL = "MESSAGE_BUS";

    private final MessageSender messageSender;
    TokenEvent(String exchageName) {
        this.exchangeName = exchageName;
        messageSender = new MessageSender(System.getenv(URL),System.getenv(exchangeName));
    }

    public void createEvent(Token token){
        try{
                messageSender.initialize();
                JsonObject jsonObject = Json.createObjectBuilder()
                        .add("id",token.id())
                        .add("token",token.token())
                        .add("usuario",token.userName())
                        .add("vigencia",token.expirationDate().toString())
                        .add("roles", Json.createArrayBuilder(token.rols()))
                        .add("emisor",token.issuer())
                        .add("fechaEmision",token.issuerDate().toString())
                        .build();

               messageSender.send(jsonObject);
        } catch (Exception e){
            e.printStackTrace();
            throw new WebApplicationException(
                    String.format("MESSAGE_BUS -> %s - %s - %s",System.getenv(URL),System.getenv(exchangeName),
                            e.getMessage()),Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
