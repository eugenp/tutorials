package org.baeldung;

import static io.gatling.javaapi.core.CoreDsl.bodyString;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.gatling.javaapi.http.HttpRequestActionBuilder;

public class SimulationUtils {

    private SimulationUtils() {
    }

    public static PopulationBuilder buildScenario(String scenarioName, ChainBuilder request, double tps, int rampUpSeconds, int durationSeconds) {
        return CoreDsl.scenario(scenarioName)
          .exec(request)
          .injectOpen(rampUsersPerSec(0).to(tps)
            .during(rampUpSeconds), constantUsersPerSec(tps).during(durationSeconds - rampUpSeconds - rampUpSeconds), rampUsersPerSec(tps).to(0)
            .during(rampUpSeconds))
          .protocols(getHttpProtocol());
    }

    public static ChainBuilder simpleGetRequest(String requestName, String requestPath, int expectedResponseStatus) {
        HttpRequestActionBuilder request = http(requestName).get(requestPath)
          .check(status().is(expectedResponseStatus))
          .check(bodyString().optional()
            .saveAs("sBodyString"));

        return exec(Session::markAsSucceeded).exec(request)
          .doIf(Session::isFailed)
          .then(exec(session -> {
              System.out.println("***Failure on [" + requestPath + "] endpoint:");
              System.out.print("Gatling Session Data: ");
              System.out.println(session.getString("sBodyString"));
              return session;
          }));
    }

    private static HttpProtocolBuilder getHttpProtocol() {
        return HttpDsl.http.baseUrl("http://localhost:8080")
          .acceptHeader("application/json")
          .disableCaching()
          .disableFollowRedirect()
          .userAgentHeader("Gatling/Performance Test");
    }
}
