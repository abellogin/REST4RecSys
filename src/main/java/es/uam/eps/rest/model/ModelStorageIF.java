package es.uam.eps.rest.model;

import java.util.Collection;

/**
 * Interface to encapsulate all the methods related to storing data.
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public interface ModelStorageIF<U, I, E> {

    public boolean existsUser(U uid);

    public U addUser(User u);

    public User removeUser(U uid);

    public User getUser(U uid);

    public Collection<User> getAllUsers();

    public boolean existsItem(I iid);

    public I addItem(Item i);

    public Item removeItem(I iid);

    public Item getItem(I iid);

    public Collection<Item> getAllItems();

    public E addEvent(U uid, I iid, Event e);

    public Event getEvent(E eid);

    public Collection<Event> getAllEvents();

    public Collection<Event> getEvents(U uid, I iid);

    public Collection<Event> getUserEvents(U uid);

    public Collection<Event> getItemEvents(I iid);

    public Statistics getStatistics();
}
