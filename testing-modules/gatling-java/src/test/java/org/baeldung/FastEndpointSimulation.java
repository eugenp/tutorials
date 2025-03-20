package org.baeldung;

import static io.gatling.javaapi.core.CoreDsl.details;

import io.gatling.javaapi.core.Simulation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FastEndpointSimulation extends Simulation {

    public FastEndpointSimulation() {
        setUp(SimulationUtils.buildScenario("getFastResponses", SimulationUtils.simpleGetRequest("request_fast_endpoint", "/api/fast-response", 200), 200, 30,
            180)).assertions(details("request_fast_endpoint").successfulRequests()
            .percent()
            .gt(95.00), details("request_fast_endpoint").responseTime()
            .max()
            .lte(10000));
    }
}
