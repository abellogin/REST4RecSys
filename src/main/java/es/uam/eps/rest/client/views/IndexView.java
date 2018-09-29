package es.uam.eps.rest.client.views;

import io.dropwizard.views.View;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public class IndexView extends View {

    public IndexView() {
        super("index.ftl");
    }
}
