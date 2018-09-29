package es.uam.eps.rest.client.views;

import es.uam.eps.rest.model.Event;
import java.util.Collection;

import io.dropwizard.views.View;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public class EventsView extends View {

    private final Collection<Event> events;

    public EventsView(Collection<Event> e) {
        super("events.ftl");
        this.events = e;
    }

    public Collection<Event> getEvents() {
        return events;
    }
}
