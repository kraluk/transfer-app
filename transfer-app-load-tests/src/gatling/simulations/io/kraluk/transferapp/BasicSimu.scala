package io.kraluk.transferapp

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class BasicSimu extends Simulation {

  val httpConfiguration: HttpProtocolBuilder = http
    .baseUrl("http://localhost:5050")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val testScenario: ScenarioBuilder = scenario("BasicSimulation")
    .exec(http("simple_request")
      .get("/test/tadek"))
    .pause(1)

  setUp(
    testScenario.inject(atOnceUsers(1000))
  ).protocols(httpConfiguration)
}