package es.uam.eps.rest.client.views;

import es.uam.eps.rest.model.Item;
import java.util.Collection;

import io.dropwizard.views.View;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public class ItemsView extends View {

    private final Collection<Item> items;

    public ItemsView(Collection<Item> i) {
        super("items.ftl");
        this.items = i;
    }

    public Collection<Item> getItems() {
        return items;
    }
}
