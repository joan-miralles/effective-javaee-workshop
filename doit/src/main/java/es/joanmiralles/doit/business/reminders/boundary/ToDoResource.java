package es.joanmiralles.doit.business.reminders.boundary;

import es.joanmiralles.doit.business.reminders.entity.ToDo;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class ToDoResource {

    long id;
    ToDoManager manager;

    public ToDoResource(long id, ToDoManager manager) {
        this.id = id;
        this.manager = manager;
    }

    @GET
    public ToDo find() {
        return manager.findById(id);
    }


    @DELETE
    public void delete() {
        manager.delete(id);
    }

    @PUT
    public ToDo update(ToDo toDo) {
        toDo.setId(id);
        return manager.save(toDo);
    }

    @PUT
    @Path("status")
    public Response statusUpdate(JsonObject statusUpdate) {
        if (!statusUpdate.containsKey("done")) {
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    header("reason", "JSON should contains field done").
                    build();
        }
        boolean done = statusUpdate.getBoolean("done");
        ToDo toDo = manager.updateStatus(id, done);
        if (toDo == null) {
            return Response.status(Response.Status.BAD_REQUEST).header("reason", "todo with id" + id + " does not exist.").build();
        } else {
            return Response.ok(toDo).build();
        }
    }


}
