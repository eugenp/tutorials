package org.baeldung.gatling.scala

import io.gatling.core.Predef.*
import io.gatling.core.structure.{ChainBuilder, PopulationBuilder}
import io.gatling.http.Predef.http

import scala.language.postfixOps

object ScenariosProvider {

    private val httpProtocol = http.baseUrl("http://localhost:8080").disableCaching.disableFollowRedirect

    def getScenario(scenarioName: String, request: ChainBuilder, tps: Double, rampUpSeconds: Integer, durationSeconds: Integer): PopulationBuilder = {
        scenario(scenarioName)
          .exec(request)
          .inject(
              rampUsersPerSec(0).to(tps).during(rampUpSeconds),
              constantUsersPerSec(tps).during(durationSeconds-rampUpSeconds-rampUpSeconds),
              rampUsersPerSec(tps).to(0).during(rampUpSeconds)
          )
          .protocols(httpProtocol)
    }
}
