package nl.firepy.examples.liberty;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * This class servers as the handler for requests to the path /cats
 */
@ApplicationScoped
@Path("/cats")
public class CatFacade {
    
    @Inject
    private CatService catService;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getCats(@QueryParam("name") String catName) {
        if(catName == null || catName.isBlank()) {
            return Response.ok(catService.getCats()).build();
        } else {
            Optional<CatDto> cat = catService.findByName(catName);
            if (cat.isPresent()) {
                return Response.ok(List.of(cat)).build();
            } else {
                return Response.status(204).entity(List.of()).build();
            }
        }
    }
}