package com.baeldung.hello.play

import play.api.mvc.{DefaultActionBuilder, PlayBodyParsers, Results}
import play.api.routing.Router
import play.api.routing.sird._

class SimplePlayRouter(action: DefaultActionBuilder, parser: PlayBodyParsers) {
  val router = Router.from {
    case GET(p"/api/play") =>
      action(parser.default) { request =>
        Results.Ok("Response from Play Simple Router")
      }
  }
}
