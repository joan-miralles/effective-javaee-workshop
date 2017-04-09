package es.joanmiralles.doit.business.reminders.entity;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ToDoTest {

    @Test
    public void validToDo() {
        ToDo valid = new ToDo("", "available", 11);
        assertTrue(valid.isValid());
    }

    @Test
    public void invalidToDo() {
        ToDo invalid = new ToDo("", null, 12);
        assertFalse(invalid.isValid());
    }

    @Test
    public void toDoWithoutDescription() {
        ToDo invalid = new ToDo("", null, 9);
        assertTrue(invalid.isValid());
    }

}