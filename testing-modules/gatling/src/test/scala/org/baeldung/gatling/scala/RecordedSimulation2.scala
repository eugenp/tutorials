package org.baeldung.gatling.scala

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation2 extends Simulation {

    val httpProtocol = http
        .baseUrl("http://www.google.com")

    val scn = scenario("RecordedSimulation")
        .exec(http("request_0")
            .get("/"))
        .pause(5)
        .exec(http("request_1")
            .get("/"))
        .pause(4)
        .exec(http("request_2")
            .get("/"))
        .pause(2)
        .exec(http("request_3")
            .get("/"))
        .pause(2)
        .exec(http("request_4")
            .get("/"))
        .pause(1)
        .exec(http("request_5")
            .get("/"))
        .pause(2)
        .exec(http("request_6")
            .get("/"))

    setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
