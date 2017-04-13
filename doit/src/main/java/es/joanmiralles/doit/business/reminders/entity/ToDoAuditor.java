package es.joanmiralles.doit.business.reminders.entity;

import es.joanmiralles.doit.business.reminders.boundary.ChangeEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

public class ToDoAuditor {

    @Inject @ChangeEvent(ChangeEvent.Type.CREATION)
    Event<ToDo> create;

    @Inject @ChangeEvent(ChangeEvent.Type.UPDATE)
    Event<ToDo> update;

    @PostPersist
    public void onCreate(ToDo todo) {
        this.create.fire(todo);
    }

    @PostUpdate
    public void onUpdate(ToDo todo) {
        this.update.fire(todo);
    }
}
