package es.joanmiralles.doit.presentation;

import es.joanmiralles.doit.business.reminders.boundary.ToDoManager;
import es.joanmiralles.doit.business.reminders.entity.ToDo;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@ManagedBean
public class Index {

    @Inject
    ToDoManager manager;

    @Inject
    Validator validator;

    ToDo toDo;

    @PostConstruct
    public void init() {
        this.toDo = new ToDo();
    }

    private void showValidationError(String content) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, content, content);
        FacesContext.getCurrentInstance().addMessage("", message);
    }

    public ToDo getToDo() {
        return this.toDo;
    }

    public String save() {
        Set<ConstraintViolation<ToDo>> violations = validator.validate(this.toDo);
        for (ConstraintViolation violation : violations) {
            this.showValidationError(violation.getMessage());
        }
        if (violations.isEmpty()) {
            this.manager.save(toDo);
        }
        return null;
    }

    public List<ToDo> getToDos() {
        return this.manager.all();
    }
}
