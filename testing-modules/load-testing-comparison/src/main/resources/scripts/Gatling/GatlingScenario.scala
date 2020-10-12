package com.baeldung

import scala.util._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class RewardsScenario extends Simulation {

  def randCustId() = java.util.concurrent.ThreadLocalRandom.current().nextInt(1, 10000)
  
  val httpProtocol = http.baseUrl("http://localhost:8080")
  
  val scn = scenario("RewardsScenario")
	.repeat(100){

		exec(http("transactions_add")
		  .post("/transactions/add/")
		  .body(StringBody(_ => s"""{ "customerRewardsId":null,"customerId":"${randCustId()}","transactionDate":null }""")).asJson
		.check(jsonPath("$.id").saveAs("txnId"))
		.check(jsonPath("$.transactionDate").saveAs("txtDate"))
		.check(jsonPath("$.customerId").saveAs("custId")))
		
		.exec(http("get_reward")
		  .get("/rewards/find/${custId}")
		  .check(jsonPath("$.id").optional.saveAs("rwdId")))
		
		.doIf("${rwdId.isUndefined()}"){
			exec(http("rewards_add")
			  .post("/rewards/add")
			  .body(StringBody("""{ "customerId": "${custId}" }""")).asJson
			.check(jsonPath("$.id").saveAs("rwdId")))
		}
		
		.exec(http("transactions_update")
		  .post("/transactions/add/")
		  .body(StringBody("""{ "customerRewardsId":"${rwdId}","customerId":"${custId}","transactionDate":"${txtDate}" }""")).asJson)
		
		.exec(http("get_transactions")
		  .get("/transactions/findAll/${rwdId}"))

		.exec(_.removeAll("txnId", "txtDate", "custId", "rwdId"))
	}
  setUp(
    scn.inject(atOnceUsers(100))
  ).protocols(httpProtocol)
}
