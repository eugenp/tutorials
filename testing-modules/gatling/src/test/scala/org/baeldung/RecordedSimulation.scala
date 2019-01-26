package org.baeldung

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

    val httpProtocol = http
        .baseURL("http://computer-database.gatling.io")
        .inferHtmlResources(BlackList(""".*\.css""", """.*\.js""", """.*\.ico"""), WhiteList())
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")




    val scn = scenario("RecordedSimulation")
        .exec(http("request_0")
            .get("/"))
        .pause(5)
        .exec(http("request_1")
            .get("/computers?f=amstrad"))
        .pause(4)
        .exec(http("request_2")
            .get("/computers/412"))
        .pause(2)
        .exec(http("request_3")
            .get("/"))
        .pause(2)
        .exec(http("request_4")
            .get("/computers?p=1"))
        .pause(1)
        .exec(http("request_5")
            .get("/computers?p=2"))
        .pause(2)
        .exec(http("request_6")
            .get("/computers?p=3"))

    setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
