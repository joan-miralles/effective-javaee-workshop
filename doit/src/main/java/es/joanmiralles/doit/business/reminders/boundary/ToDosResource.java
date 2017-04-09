package es.joanmiralles.doit.business.reminders.boundary;

import es.joanmiralles.doit.business.reminders.entity.ToDo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Stateless
@Path("todos")
public class ToDosResource {

    @Inject
    ToDoManager manager;

    @Path("{id}")
    public ToDoResource find(@PathParam("id") long id) {
        return new ToDoResource(id, manager);
    }

    @GET
    public List<ToDo> all() {
        return this.manager.all();
    }

    @POST
    public Response save(@Valid ToDo todo, @Context UriInfo info) {
        ToDo saved = this.manager.save(todo);
        long id = saved.getId();
        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).build();
    }

}
