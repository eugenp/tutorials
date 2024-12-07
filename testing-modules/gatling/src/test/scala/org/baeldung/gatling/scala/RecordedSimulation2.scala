package org.baeldung.gatling.scala

import io.gatling.core.Predef.{details, _}
import org.baeldung.gatling.scala.ChainRequestsProvider.simpleRequest
import org.baeldung.gatling.scala.ScenariosProvider.getScenario

class RecordedSimulation2 extends Simulation {

    setUp(
        getScenario("getExistingEndpoint", simpleRequest("request_status_endpoint", "/health/status", 200), 50, 10, 60),
        getScenario("nonExistingEndpoint", simpleRequest("request_wrong_endpoint", "/health/status1", 200), 5, 10, 60)
    ).assertions(
        details("request_status_endpoint").successfulRequests.percent.gt(99.99),
        details("request_status_endpoint").responseTime.percentile4.lt(20),
        details("request_status_endpoint").requestsPerSec.gt(40),
        details("request_wrong_endpoint").successfulRequests.percent.lt(1),
        details("request_wrong_endpoint").responseTime.percentile4.lt(20)
    )
}
