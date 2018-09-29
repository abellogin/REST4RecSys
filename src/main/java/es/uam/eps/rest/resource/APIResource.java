package es.uam.eps.rest.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.uam.eps.rest.model.Event;
import es.uam.eps.rest.model.Item;
import es.uam.eps.rest.model.ModelStorageIF;
import es.uam.eps.rest.model.Statistics;
import es.uam.eps.rest.model.User;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
@Path("/v1/")
public abstract class APIResource {

    protected static final int DEF_TRAIN_EVENTS = 100;
    protected ModelStorageIF<String, String, String> model;

    public APIResource(ModelStorageIF<String, String, String> model) {
        this.model = model;
    }

    /////////////////////////////////////////////// 
    ///                  USER
    ///////////////////////////////////////////////
    @POST
    @Path("/user/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addUser(User user) {
        if (user != null) {
            UUID id = UUID.randomUUID();
            user.setUid(id.toString());
            return model.addUser(user);
        }
        return "-1";
    }

    @GET
    @Path("/user/get/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("uid") String userId) {
        if (userId != null) {
            return model.getUser(userId);
        }
        return null;
    }

    @GET
    @Path("/user/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> getAllUsers() {
        return model.getAllUsers();
    }

    @DELETE
    @Path("/user/delete/{uid}")
    public String deleteUser(@PathParam("uid") String userId) {
        if (userId != null) {
            return model.removeUser(userId).getUid();
        }

        return "-1";
    }

    @GET
    @Path("/user/get/{uid}/events")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Event> getUserEvents(@PathParam("uid") String userId) {
        return model.getUserEvents(userId);
    }

    @GET
    @Path("/user/get/{uid}/recommendations")
    @Produces(MediaType.APPLICATION_JSON)
    public abstract List<Item> getUserRecommendations(@PathParam("uid") String userId);

    /////////////////////////////////////////////// 
    ///                  ITEM
    ///////////////////////////////////////////////	
    @POST
    @Path("/item/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addItem(Item item) {
        if (item != null) {
            UUID id = UUID.randomUUID();
            item.setIid(id.toString());
            return model.addItem(item);
        }
        return "-1";
    }

    @GET
    @Path("/item/get/{iid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item getItem(@PathParam("iid") String itemId) {
        if (itemId != null) {
            return model.getItem(itemId);
        }
        return null;
    }

    @GET
    @Path("/item/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Item> getAllItems() {
        return model.getAllItems();
    }

    @DELETE
    @Path("/item/delete/{iid}")
    public String deleteItem(@PathParam("iid") String itemId) {
        if (itemId != null) {
            return model.removeItem(itemId).getIid();
        }

        return "-1";
    }

    @GET
    @Path("/item/get/{iid}/events")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Event> getItemEvents(@PathParam("iid") String itemId) {
        return model.getItemEvents(itemId);
    }

    @POST
    @Path("/event/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addEvent(Event e) {
        return addEvent(e.getUid(), e.getIid(), e);
    }

    @POST
    @Path("/event/add/user/{uid}/item/{iid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addEvent(@PathParam("uid") String userId, @PathParam("iid") String itemId,
            Event e) {
        String id = model.addEvent(userId, itemId, e);
        if (model.getAllEvents().size() % DEF_TRAIN_EVENTS == 0) {
            train(null);
        }
        return id;
    }

    @GET
    @Path("/event/get/user/{uid}/item/{iid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Event> getEvents(@PathParam("uid") String userId, @PathParam("iid") String itemId) {
        return model.getEvents(userId, itemId);
    }

    @GET
    @Path("/event/get/{eid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Event getEvent(@PathParam("eid") String eventId) {
        if (eventId != null) {
            return model.getEvent(eventId);
        }
        return null;
    }

    @GET
    @Path("/event/get/user/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Event> getEventsFromUser(@PathParam("uid") String userId) {
        return getUserEvents(userId);
    }

    @GET
    @Path("/event/get/user/{uid}/rating")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Event> getEventUserRating(@PathParam("uid") String userId) {
        Collection<Event> collEvents = new ArrayList<>();
        for (Event iEvent : model.getAllEvents()) {
            if (iEvent.getUid().equals(userId)) {
                if (iEvent.getType().equals(Event.RATING_TYPE)) {
                    collEvents.add(iEvent);
                }
            }
        }
        return collEvents;
    }

    @GET
    @Path("/event/get/user/{uid}/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Event> getEventUserCount(@PathParam("uid") String userId) {
        Collection<Event> collEvents = new ArrayList<>();
        for (Event iEvent : model.getAllEvents()) {
            if (iEvent.getUid().equals(userId)) {
                if (iEvent.getType().equals(Event.COUNT_TYPE)) {
                    collEvents.add(iEvent);
                }
            }
        }
        return collEvents;
    }

    @GET
    @Path("/event/get/item/{iid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Event> getEventsFromItem(@PathParam("iid") String itemId) {
        return getItemEvents(itemId);
    }

    @GET
    @Path("/event/get/item/{iid}/rating")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Event> getEventItemRating(@PathParam("iid") String itemId) {
        Collection<Event> collEvents = new ArrayList<>();
        for (Event iEvent : model.getAllEvents()) {
            if (iEvent.getIid().equals(itemId)) {
                if (iEvent.getType().equals(Event.RATING_TYPE)) {
                    collEvents.add(iEvent);
                }
            }
        }
        return collEvents;
    }

    @GET
    @Path("/event/get/item/{iid}/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Event> getEventItemCount(@PathParam("iid") String itemId) {
        Collection<Event> collEvents = new ArrayList<>();
        for (Event iEvent : model.getAllEvents()) {
            if (iEvent.getIid().equals(itemId)) {
                if (iEvent.getType().equals(Event.COUNT_TYPE)) {
                    collEvents.add(iEvent);
                }
            }
        }
        return collEvents;
    }

    @GET
    @Path("/event/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Event> getAllEvents() {
        return model.getAllEvents();
    }

    @GET
    @Path("/train")
    public abstract void train(@QueryParam("method") String recMethod);

    @GET
    @Path("/statistics/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Statistics getStatistics() {
        return model.getStatistics();
    }
}
