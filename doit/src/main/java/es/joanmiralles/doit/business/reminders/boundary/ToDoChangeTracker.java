package es.joanmiralles.doit.business.reminders.boundary;

import es.joanmiralles.doit.business.encoders.JsonEncoder;
import es.joanmiralles.doit.business.reminders.entity.ToDo;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Singleton
@ServerEndpoint(value = "/changes", encoders = {JsonEncoder.class})
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ToDoChangeTracker {

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose() {
        this.session = null;
    }

    public void onToDoChange(@Observes(during = TransactionPhase.AFTER_SUCCESS) @ChangeEvent(ChangeEvent.Type.CREATION) ToDo toDo) throws EncodeException {
        if (session != null && session.isOpen()) {
            try {
                JsonObject event = Json.createObjectBuilder().
                        add("id", toDo.getId()).
                        add("cause", "creation").
                        build();
                this.session.getBasicRemote().sendObject(event);
            } catch (IOException e) {
                // we ignore this
            }
        }
    }
}
