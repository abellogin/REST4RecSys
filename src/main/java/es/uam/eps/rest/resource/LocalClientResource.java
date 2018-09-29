package es.uam.eps.rest.resource;

import es.uam.eps.rest.client.views.AddEventView;
import es.uam.eps.rest.client.views.UsersView;
import es.uam.eps.rest.client.views.ItemsView;
import es.uam.eps.rest.client.views.IndexView;
import es.uam.eps.rest.client.views.EventsView;
import es.uam.eps.rest.client.views.AddUserView;
import es.uam.eps.rest.client.views.AddItemView;
import es.uam.eps.rest.client.views.StatisticsView;
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
import es.uam.eps.rest.model.ModelStorageIF;
import es.uam.eps.rest.model.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
@Path("/local_interface/")
public class LocalClientResource {

    public static final int DEF_TRAIN_EVENTS = 100;
    private ModelStorageIF<String, String, String> model;

    public LocalClientResource(ModelStorageIF<String, String, String> model) {
        this.model = model;
    }

    @GET
    @Path("/index")
    public IndexView index() {
        return new IndexView();
    }

    @GET
    @Path("/get/users")
    public UsersView interfaceGetUsers() {
        return new UsersView(model.getAllUsers());
    }

    @GET
    @Path("/get/items")
    public ItemsView interfaceGetItems() {
        return new ItemsView(model.getAllItems());
    }

    @GET
    @Path("/get/events")
    public EventsView interfaceGetEvents() {
        return new EventsView(model.getAllEvents());
    }

    @GET
    @Path("/get/stats")
    public StatisticsView interfaceGetStats() {
        return new StatisticsView(model.getStatistics());
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

        model.addUser(u);
        return Response.seeOther(UriBuilder.fromUri("./local_interface/get/users").build()).build();
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

        model.addItem(i);
        return Response.seeOther(UriBuilder.fromUri("./local_interface/get/items").build()).build();
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

        model.addEvent(uid, iid, e);
        return Response.seeOther(UriBuilder.fromUri("./local_interface/get/events").build()).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadDataset(@FormDataParam("data_file") InputStream uploadedInputStream, @FormDataParam("data_file") FormDataContentDisposition fileDetail) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(uploadedInputStream));
            String line = null;
            while ((line = in.readLine()) != null) {
                String del = "\t";
                String[] toks = null;
                if (line.contains(del)) {
                    toks = line.split(del);
                } else {
                    del = "::";
                    if (line.contains(del)) {
                        toks = line.split(del);
                    }
                }
                if (toks != null) {
                    String uid = toks[0];
                    String iid = toks[1];
                    double value = Double.parseDouble(toks[2]);
                    String timestamp = System.currentTimeMillis() + "";
                    if (toks.length == 4) {
                        timestamp = toks[3];
                    }

                    Event e = new Event();
                    UUID id = UUID.randomUUID();
                    e.setEid(id.toString());
                    e.setUid(uid);
                    e.setIid(iid);
                    e.setType(Event.RATING_TYPE);
                    e.setValue(value);
                    e.setTimestamp(Long.parseLong(timestamp));

                    model.addEvent(uid, iid, e);
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        return Response.seeOther(UriBuilder.fromUri("./local_interface/get/events").build()).build();
    }
}
