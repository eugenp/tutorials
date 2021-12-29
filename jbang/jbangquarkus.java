///usr/bin/env jbang "$0" "$@" ; exit $?
// Update the Quarkus version to what you want here or run jbang with
// `-Dquarkus.version=<version>` to override it.
//DEPS io.quarkus:quarkus-bom:${quarkus.version:2.4.0.Final}@pom
//DEPS io.quarkus:quarkus-resteasy
//JAVAC_OPTIONS -parameters

//FILES META-INF/resources/index.html=index.html

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
@ApplicationScoped
public class jbangquarkus {
    @GET
    public String sayHello() {
        return "Hello from Quarkus with jbang.dev";
    }
}
