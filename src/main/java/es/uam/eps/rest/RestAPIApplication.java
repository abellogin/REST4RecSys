package es.uam.eps.rest;

import es.uam.eps.rest.model.ModelStorageIF;
import es.uam.eps.rest.model.SimpleModelStorage;
import es.uam.eps.rest.resource.APIRankSysResource;
import es.uam.eps.rest.resource.LocalClientResource;
import es.uam.eps.rest.resource.MainClientResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 * Main entry point for the REST application.
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public class RestAPIApplication extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new RestAPIApplication().run(args);
    }

    @Override
    public void run(Configuration configuration, Environment environment) {
        // change here if other model storage is used
        ModelStorageIF<String, String, String> model = new SimpleModelStorage(true);
        environment.jersey().register(new APIRankSysResource(model));
        environment.jersey().register(new LocalClientResource(model));
        environment.jersey().register(new MainClientResource());

        environment.jersey().register(MultiPartFeature.class);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<>());
    }
}
