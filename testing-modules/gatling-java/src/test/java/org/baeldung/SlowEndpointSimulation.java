package org.baeldung;

import static io.gatling.javaapi.core.CoreDsl.details;

import io.gatling.javaapi.core.Simulation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlowEndpointSimulation extends Simulation {

    public SlowEndpointSimulation() {
        setUp(SimulationUtils.buildScenario("getSlowResponses", SimulationUtils.simpleGetRequest("request_slow_endpoint", "/api/slow-response", 200), 120, 30,
          300)).assertions(details("request_slow_endpoint").successfulRequests()
          .percent()
          .gt(95.00), details("request_slow_endpoint").responseTime()
          .max()
          .lte(10000));
    }
}
