package es.uam.eps.rest.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public class SimpleModelStorage implements ModelStorageIF<String, String, String> {

    private Map<String, User> users;
    private Map<String, Item> items;
    private Map<String, Event> events;
    private boolean createIfNotExists;

    public SimpleModelStorage(boolean createIfNotExists) {
        users = new ConcurrentHashMap<>();
        items = new ConcurrentHashMap<>();
        events = new ConcurrentHashMap<>();

        this.createIfNotExists = createIfNotExists;
    }

    @Override
    public boolean existsUser(String uid) {
        return users.containsKey(uid);
    }

    @Override
    public String addUser(User u) {
        if (!existsUser(u.getUid())) {
            users.put(u.getUid(), u);
            return u.getUid();
        }
        return null;
    }

    @Override
    public User removeUser(String uid) {
        if (existsUser(uid)) {
            return users.remove(uid);
        }
        return null;
    }

    @Override
    public User getUser(String uid) {
        return users.get(uid);
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public boolean existsItem(String iid) {
        return items.containsKey(iid);
    }

    @Override
    public String addItem(Item i) {
        if (!existsItem(i.getIid())) {
            items.put(i.getIid(), i);
            return i.getIid();
        }
        return null;
    }

    @Override
    public Item removeItem(String iid) {
        if (existsItem(iid)) {
            return items.remove(iid);
        }
        return null;
    }

    @Override
    public Item getItem(String iid) {
        return items.get(iid);
    }

    @Override
    public Collection<Item> getAllItems() {
        return items.values();
    }

    @Override
    public String addEvent(String uid, String iid, Event e) {
        if (!existsUser(uid)) {
            if (createIfNotExists) {
                User u = new User();
                u.setUid(uid);
                addUser(u);
            } else {
                return null;
            }
        }
        if (!existsItem(iid)) {
            if (createIfNotExists) {
                Item i = new Item();
                i.setIid(iid);
                addItem(i);
            } else {
                return null;
            }
        }
        // make sure the values inside the object match those passed as parameter
        e.setIid(iid);
        e.setUid(uid);
        // if a rating event already exists with the same content, we do not add it
        for (Event iEvent : events.values()) {
            if (iEvent.getUid().equals(uid) && iEvent.getIid().equals(iid) && e.getType().equals(Event.RATING_TYPE)) {
                return null;
            }
        }
        if (e.getType().equals(Event.RATING_TYPE)) {
            items.get(iid).setNumValoraciones(items.get(iid).getNumValoraciones() + 1);
            items.get(iid).setValue((items.get(iid).getValue() * (items.get(iid).getNumValoraciones() - 1) + e.getValue()) / items.get(iid).getNumValoraciones());
        } else if (e.getType().equals(Event.COUNT_TYPE)) {
            items.get(iid).setCount(items.get(iid).getCount() + 1);
        }
        // create a random id
        UUID id = UUID.randomUUID();
        e.setEid(id.toString());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        e.setTimestamp(timestamp.getTime());
        events.put(e.getEid(), e);
        return e.getEid();
    }

    @Override
    public Event getEvent(String eid) {
        return events.get(eid);
    }

    @Override
    public Collection<Event> getAllEvents() {
        return events.values();
    }

    @Override
    public Collection<Event> getEvents(String uid, String iid) {
        Collection<Event> collEvents = new ArrayList<>();
        for (Event e : events.values()) {
            if (e.getUid().equals(uid) && e.getIid().equals(iid)) {
                collEvents.add(e);
            }
        }
        return collEvents;
    }

    @Override
    public Collection<Event> getUserEvents(String uid) {
        Collection<Event> collEvents = new ArrayList<>();
        for (Event uEvent : events.values()) {
            if (uEvent.getUid().equals(uid)) {
                collEvents.add(uEvent);
            }
        }
        return collEvents;
    }

    @Override
    public Collection<Event> getItemEvents(String iid) {
        Collection<Event> collEvents = new ArrayList<>();
        for (Event iEvent : events.values()) {
            if (iEvent.getIid().equals(iid)) {
                collEvents.add(iEvent);
            }
        }
        return collEvents;
    }

    @Override
    public Statistics getStatistics() {
        int numEventRating = 0;
        int numEventCount = 0;
        for (Event e : events.values()) {
            if (e.getType().equals(Event.RATING_TYPE)) {
                numEventRating += 1;
            } else if (e.getType().equals(Event.COUNT_TYPE)) {
                numEventCount += 1;
            }
        }

        return new Statistics(users.size(), items.size(), events.size(), numEventRating, numEventCount);
    }

}
