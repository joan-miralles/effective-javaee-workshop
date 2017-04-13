package es.joanmiralles.doit.business.reminders.entity;

import es.joanmiralles.doit.business.CrossCheck;
import es.joanmiralles.doit.business.ValidEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQuery(name = ToDo.FIND_ALL, query = "SELECT t FROM ToDo t")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@CrossCheck
public class ToDo implements ValidEntity {

    @Id
    @GeneratedValue
    private long id;

    static final String PREFIX = "reminders.entity.ToDo.";
    public static final String FIND_ALL = PREFIX + "findAll";

    @NotNull
    @Size(min = 1, max = 256)
    private String caption;
    private String description;
    private int priority;
    private boolean done;

    @Version
    private long version;

    public ToDo(String caption, String description, int priority) {
        this.caption = caption;
        this.description = description;
        this.priority = priority;
    }

    public ToDo() {
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean isValid() {
        if (this.priority <= 10) {
            return true;
        }
        return this.description != null && !this.description.isEmpty();
    }

}
