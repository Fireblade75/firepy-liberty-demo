package nl.firepy.examples.liberty;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * This class can remain empty, but is required for the JAX-RS application to function
 */
@ApplicationPath("/")
public class CatApplication extends Application {
    
}
