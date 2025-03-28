package org.baeldung;

import static io.gatling.javaapi.core.CoreDsl.details;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.Simulation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlowEndpointSimulation extends Simulation {

    public SlowEndpointSimulation() {
        ChainBuilder getSlowEndpointChainBuilder = SimulationUtils.simpleGetRequest("request_slow_endpoint", "/api/slow-response", 200);
        PopulationBuilder slowResponsesPopulationBuilder = SimulationUtils.buildScenario("getSlowResponses", getSlowEndpointChainBuilder, 120, 30, 300);

        setUp(slowResponsesPopulationBuilder)
          .assertions(
            details("request_slow_endpoint").successfulRequests().percent().gt(95.00),
            details("request_slow_endpoint").responseTime().max().lte(10000)
          );
    }
}
