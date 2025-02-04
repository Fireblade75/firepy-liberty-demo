package nl.firepy.examples.liberty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/cats")
public class CatFacade {
    
    @GET
    @Produces(value = MediaType.TEXT_PLAIN)
    public Response getCat() {
        return Response.ok("black cat").build();
    }
}