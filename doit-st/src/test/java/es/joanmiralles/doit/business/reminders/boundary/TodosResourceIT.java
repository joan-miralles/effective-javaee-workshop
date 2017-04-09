package es.joanmiralles.doit.business.reminders.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import org.junit.Rule;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TodosResourceIT {

    @Rule
    public JAXRSClientProvider provider = JAXRSClientProvider.buildWithURI("http://localhost:8080/doit/api/todos");

    @Test
    public void crud() {
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject todoToCreate = todoBuilder.
                add("caption", "implement").
                add("priority", 10).
                build();
        //create
        Response postResponse = this.provider.target().request().post(Entity.json(todoToCreate));
        assertThat(postResponse.getStatus(), is(201));
        String location = postResponse.getHeaderString("Location");
        System.out.println("location = " + location);

        //find
        JsonObject dedicatedTodo = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        assertTrue(dedicatedTodo.getString("caption").contains("implement"));

        //findAll
        Response response = this.provider.target().request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonArray allTodos = response.readEntity(JsonArray.class);
        System.out.println("payload = " + allTodos);
        assertFalse(allTodos.isEmpty());

        JsonObject todo = allTodos.getJsonObject(0);
        assertTrue(todo.getString("caption").startsWith("implement"));

        // update
        JsonObjectBuilder updateBuilder = Json.createObjectBuilder();
        JsonObject updated = updateBuilder.
                add("caption", "implemented").
                add("priority", 10).
                build();
        Response updatedResponse = this.provider.client().
                target(location).request(MediaType.APPLICATION_JSON).put(Entity.json(updated));
        assertThat(updatedResponse.getStatus(), is(200));

        //updated again
        updated = updateBuilder.
                add("caption", "implemented").
                add("priority", 10).
                build();

        updatedResponse = this.provider.client().
                target(location).request(MediaType.APPLICATION_JSON).put(Entity.json(updated));
        assertThat(updatedResponse.getStatus(), is(409));
        String conflictInformation = updatedResponse.getHeaderString("cause");
        System.out.println("conflictInformation = " + conflictInformation);

        // find it again
        JsonObject updatedTodo = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        assertTrue(updatedTodo.getString("caption").contains("implemented"));

        // update status
        JsonObjectBuilder statusBuilder = Json.createObjectBuilder();
        JsonObject status = statusBuilder.
                add("done", true).
                build();
        this.provider.client().
                target(location).
                path("status").
                request(MediaType.APPLICATION_JSON).
                put(Entity.json(status));

        // verify status
        updatedTodo = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        assertThat(updatedTodo.getBoolean("done"), is(true));

        // update no-existing status
        JsonObjectBuilder notExistingBuilder = Json.createObjectBuilder();
        status = notExistingBuilder.
                add("done", true).
                build();

        Response notExistingResponse = this.provider.target().
                path("-42").
                path("status").
                request(MediaType.APPLICATION_JSON).
                put(Entity.json(status));
        assertThat(notExistingResponse.getStatus(), is(400));
        assertFalse(notExistingResponse.getHeaderString("reason").isEmpty());

        // update malformed status
        notExistingBuilder = Json.createObjectBuilder();
        status = notExistingBuilder.
                add("something wrong", true).
                build();

        Response malformedResponse = this.provider.
                client().
                target(location).
                path("status").
                request(MediaType.APPLICATION_JSON).
                put(Entity.json(status));
        assertThat(malformedResponse.getStatus(), is(400));
        assertFalse(malformedResponse.getHeaderString("reason").isEmpty());

        // deleting not existing
        Response deleteResponse = this.provider.target().
                path("42").
                request(MediaType.APPLICATION_JSON).
                delete();

        assertThat(deleteResponse.getStatus(), is(204));
    }

    @Test
    public void createToDoWithoutCaption() {
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject todoToCreate = todoBuilder.
                add("priority", 42).
                build();
        //create
        Response postResponse = this.provider.target().request().post(Entity.json(todoToCreate));
        postResponse.getHeaders().entrySet().forEach(System.out::println);
        assertThat(postResponse.getStatus(), is(400));
    }

    @Test
    public void createAValidToDo() {
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject todoToCreate = todoBuilder.
                add("caption", "valid caption").
                add("description", "some description").
                add("priority", 11).
                build();
        //create
        Response postResponse = this.provider.target().request().post(Entity.json(todoToCreate));
        postResponse.getHeaders().entrySet().forEach(System.out::println);
        assertThat(postResponse.getStatus(), is(201));
    }

    @Test
    public void createToDoWithHighPriorityWithoutDescription() {
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject todoToCreate = todoBuilder.
                add("caption", "valid caption").
                add("priority", 12).
                build();
        //create
        Response postResponse = this.provider.target().request().post(Entity.json(todoToCreate));
        postResponse.getHeaders().entrySet().forEach(System.out::println);
        assertThat(postResponse.getStatus(), is(400));
    }

}
