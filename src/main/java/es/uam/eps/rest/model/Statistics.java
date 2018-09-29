package es.uam.eps.rest.model;

import java.io.Serializable;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public class Statistics implements Serializable {

    private int numUsers;
    private int numItems;
    private int numEvents;
    private int numEventRating;
    private int numEventCount;

    public Statistics() {
    }

    public Statistics(int numUsers, int numItems, int numEvents, int numEventRating, int numEventCount) {
        this.numUsers = numUsers;
        this.numItems = numItems;
        this.numEvents = numEvents;
        this.numEventRating = numEventRating;
        this.numEventCount = numEventCount;
    }

    public int getNumUsers() {
        return numUsers;
    }

    public int getNumItems() {
        return numItems;
    }

    public int getNumEvents() {
        return numEvents;
    }

    public int getNumEventRating() {
        return numEventRating;
    }

    public int getNumEventCount() {
        return numEventCount;
    }

    public void setNumUsers(int numUsers) {
        this.numUsers = numUsers;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public void setNumEvents(int numEvents) {
        this.numEvents = numEvents;
    }

    public void setNumEventRating(int numEventRating) {
        this.numEventRating = numEventRating;
    }

    public void setNumEventCount(int numEventCount) {
        this.numEventCount = numEventCount;
    }
}
