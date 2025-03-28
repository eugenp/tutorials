package org.baeldung;

import static io.gatling.javaapi.core.CoreDsl.details;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.Simulation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FastEndpointSimulation extends Simulation {

    public FastEndpointSimulation() {
        ChainBuilder getFastEndpointChainBuilder = SimulationUtils.simpleGetRequest("request_fast_endpoint", "/api/fast-response", 200);
        PopulationBuilder fastResponsesPopulationBuilder = SimulationUtils.buildScenario("getFastResponses", getFastEndpointChainBuilder, 200, 30, 180);

        setUp(fastResponsesPopulationBuilder)
          .assertions(
            details("request_fast_endpoint").successfulRequests().percent().gt(95.00),
            details("request_fast_endpoint").responseTime().max().lte(10000)
          );
    }
}
