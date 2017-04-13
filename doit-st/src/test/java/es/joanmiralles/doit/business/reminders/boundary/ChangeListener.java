package es.joanmiralles.doit.business.reminders.boundary;

import javax.json.JsonObject;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ChangeListener extends Endpoint {
    private JsonObject message;
    private CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        session.addMessageHandler(JsonObject.class, (msg) -> {
            this.message = msg;
            this.latch.countDown();
        });
    }

    public JsonObject getMessage() throws InterruptedException {
        latch.await(1, TimeUnit.MINUTES);
        return this.message;
    }
}
