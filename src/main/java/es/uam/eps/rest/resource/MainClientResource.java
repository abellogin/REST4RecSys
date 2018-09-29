package es.uam.eps.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;


/**
 *
 * @author Alejandro Bellogin
 */
@Path("/")
public class MainClientResource {

    @GET
    public Response index() {
        return Response.seeOther(UriBuilder.fromUri("./local_interface/index").build()).build();
    }
}
