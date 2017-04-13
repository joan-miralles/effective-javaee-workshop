package es.joanmiralles.doit.business.reminders.control;

import es.joanmiralles.doit.business.reminders.entity.ToDo;

import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;

public class ToDoChangeTracker {

    public void onToDoChange(@Observes(during = TransactionPhase.AFTER_SUCCESS) ToDo toDo) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>toDo = " + toDo);
    }
}
