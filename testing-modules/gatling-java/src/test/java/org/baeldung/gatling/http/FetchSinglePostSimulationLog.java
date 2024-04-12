package org.baeldung.gatling.http;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class FetchSinglePostSimulationLog extends Simulation {

    public FetchSinglePostSimulationLog() {
        HttpProtocolBuilder httpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com");

        ScenarioBuilder scn = scenario("Display Full HTTP Response Body").exec(http("GET Request").get("/posts/1")
          .check(status().is(200))
          .check(bodyString().saveAs("responseBody")))
          .exec(session -> {

              String responseBody = session.getString("responseBody");
              try {
                  writeFile("response_body.log", responseBody);
              } catch (IOException e) {
                  System.err.println("error writing file");
              }
              return session;
          });
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocolBuilder);
    }

    private void writeFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            writer.newLine();
        }
    }
}
