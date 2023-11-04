package co.edu.uniquindio.ingesis.autenticacion.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jakarta.json.JsonObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
/**
 * Clase usada para la conexión al bus de mensajería y así permitir el envío de eventos a otros microservicios
 *
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
public class MessageSender {
    private Connection connection;
    private Channel channel;
    private final String url;
    private final String chanelName;
    private boolean initialize;
    public MessageSender(String url, String chanelName) {
        this.url = url;
        this.chanelName = chanelName;
        this.initialize = false;
    }

    public void initialize() throws URISyntaxException, NoSuchAlgorithmException, IOException, KeyManagementException, TimeoutException {
        if( !initialize && url != null) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(url);
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(chanelName, "fanout");
            initialize = true;
        }
    }

    public void send(JsonObject jsonObject) throws IOException {
        if( initialize ) {
            channel.basicPublish(chanelName, "", null, jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        }
    }
}

