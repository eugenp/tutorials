package org.baeldung.gatling.http;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.core.CoreDsl.*;

public class FetchSinglePostSimulation extends Simulation {

    public FetchSinglePostSimulation() {
        HttpProtocolBuilder httpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com");

        ScenarioBuilder scn = scenario("Display Full HTTP Response Body").exec(http("GET Request").get("/posts/1")
          .check(status().is(200))
          .check(bodyString().saveAs("responseBody")))
          .exec(session -> {
              System.out.println("Response Body:");
              System.out.println(session.getString("responseBody"));
              return session;
            });
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocolBuilder);
    }
}
