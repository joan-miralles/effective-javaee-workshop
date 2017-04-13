package es.joanmiralles.doit.business.reminders.boundary;

import es.joanmiralles.doit.business.logging.boundary.BoundaryLogger;
import es.joanmiralles.doit.business.reminders.entity.ToDo;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Interceptors(BoundaryLogger.class)
public class ToDoManager {
    @PersistenceContext
    EntityManager em;

    public ToDo findById(long id) {
        return this.em.find(ToDo.class, id);
    }

    public void delete(long id) {
        try {
            ToDo reference = this.em.getReference(ToDo.class, id);
            this.em.remove(reference);
        } catch (EntityNotFoundException ex) {
            // we want to remove it...
        }
    }

    public ToDo save(ToDo todo) {
        return this.em.merge(todo);
    }

    public List<ToDo> all() {
        return this.em.createNamedQuery(ToDo.FIND_ALL, ToDo.class)
                .getResultList();
    }

    public ToDo updateStatus(long id, boolean done) {
        ToDo toDo = this.findById(id);
        if (toDo == null) {
            return null;
        }
        toDo.setDone(done);
        return toDo;
    }
}
