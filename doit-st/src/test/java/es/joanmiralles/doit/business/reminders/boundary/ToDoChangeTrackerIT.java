package es.joanmiralles.doit.business.reminders.boundary;

import org.junit.Before;
import org.junit.Test;

import javax.json.JsonObject;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;

public class ToDoChangeTrackerIT {
    private WebSocketContainer container;
    private ChangeListener listener;

    @Before
    public void initContainer() throws URISyntaxException, IOException, DeploymentException {
        this.container = ContainerProvider.getWebSocketContainer();
        URI uri = new URI("ws://localhost:8080/doit/changes");
        this.listener = new ChangeListener();
        ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create().decoders(Collections.singletonList(JsonDecoder.class)).build();
        this.container.connectToServer(this.listener, clientEndpointConfig, uri);
    }

    @Test
    public void receiveNotifications() throws InterruptedException {
        JsonObject message = this.listener.getMessage();
        assertNotNull(message);
        System.out.println("message = " + message);
    }
}
