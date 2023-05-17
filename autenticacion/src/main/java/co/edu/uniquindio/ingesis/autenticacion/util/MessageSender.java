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
        if( !initialize ) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(url);
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(chanelName, "fanout");
            initialize = true;
        }
    }

    public void send(JsonObject jsonObject) throws IOException {
        channel.basicPublish(chanelName, "", null, jsonObject.toString().getBytes(StandardCharsets.UTF_8));
    }
}

