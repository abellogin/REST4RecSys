package es.uam.eps.rest.resource;

import es.uam.eps.rest.client.views.AddEventView;
import es.uam.eps.rest.client.views.UsersView;
import es.uam.eps.rest.client.views.ItemsView;
import es.uam.eps.rest.client.views.IndexView;
import es.uam.eps.rest.client.views.EventsView;
import es.uam.eps.rest.client.views.AddUserView;
import es.uam.eps.rest.client.views.AddItemView;
import es.uam.eps.rest.model.Event;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import es.uam.eps.rest.model.Item;
import es.uam.eps.rest.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 *
 * @author Alejandro Bellogin
 */
@Path("/interface/")
public class IsolatedClientResource {

    public static final int DEF_TRAIN_EVENTS = 100;

    public IsolatedClientResource() {
    }

    @GET
    @Path("/index")
    public IndexView index() {
        return new IndexView();
    }

    @GET
    @Path("/get/users")
    public UsersView interfaceGetUsers() {
        Collection<User> users = new ArrayList<>();
        // TODO
        return new UsersView(users);
    }

    @GET
    @Path("/get/items")
    public ItemsView interfaceGetItems() {
        Collection<Item> items = new ArrayList<>();
        // TODO
        return new ItemsView(items);
    }

    @GET
    @Path("/get/events")
    public EventsView interfaceGetEvents() {
        Collection<Event> events = new ArrayList<>();
        // TODO
        return new EventsView(events);
    }

    @GET
    @Path("/add/user")
    public AddUserView interfaceAddUser() {
        return new AddUserView();
    }

    @POST
    @Path("/add/user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response interfaceAddUser(@FormParam("name") String name,
            @FormParam("location") String location,
            @FormParam("email") String email,
            @FormParam("age") String age) {
        User u = new User();
        UUID id = UUID.randomUUID();
        u.setUid(id.toString());
        u.setName(name);
        u.setLocation(location);
        u.setEmail(email);
        u.setAge(age);

        // TODO
        return Response.seeOther(UriBuilder.fromUri("./interface/get/users").build()).build();
    }

    @GET
    @Path("/add/item")
    public AddItemView interfaceAddItem() {
        return new AddItemView();
    }

    @POST
    @Path("/add/item")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response interfaceAddItem(@FormParam("name") String name,
            @FormParam("content") String content) {
        Item i = new Item();
        UUID id = UUID.randomUUID();
        i.setIid(id.toString());
        i.setName(name);
        i.setContent(content);

        // TODO
        return Response.seeOther(UriBuilder.fromUri("./interface/get/items").build()).build();
    }

    @GET
    @Path("/add/event")
    public AddEventView interfaceAddEvent() {
        return new AddEventView();
    }

    @POST
    @Path("/add/event")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response interfaceAddEvent(@FormParam("uid") String uid, @FormParam("iid") String iid,
            @FormParam("type") String type, @FormParam("value") Double value) {
        Event e = new Event();
        UUID id = UUID.randomUUID();
        e.setEid(id.toString());
        e.setUid(uid);
        e.setIid(iid);
        e.setType(type);
        e.setValue(value);
        e.setTimestamp(System.currentTimeMillis());

        // TODO
        return Response.seeOther(UriBuilder.fromUri("./interface/get/events").build()).build();
    }
}
