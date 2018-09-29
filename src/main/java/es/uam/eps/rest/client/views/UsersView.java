package es.uam.eps.rest.client.views;

import es.uam.eps.rest.model.User;
import java.util.Collection;

import io.dropwizard.views.View;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public class UsersView extends View {

    private final Collection<User> users;

    public UsersView(Collection<User> u) {
        super("users.ftl");
        this.users = u;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
