package com.baeldung.finch.helloWorld

import com.twitter.finagle.Http
import com.twitter.util.Await

import io.finch._
import io.finch.circe._
import io.finch.syntax._
import io.circe.generic.auto._

object Main extends App {
  case class FullName(first: String, last: String)

  val hello: Endpoint[String] = get("hello") { Ok("Hello, World!") }
  val helloName: Endpoint[String] = post("hello" :: jsonBody[FullName]) { name: FullName =>
    Ok(s"Hello, ${name.first} ${name.last}!")
  }

  Await.ready(Http.server.serve(":8081", (hello :+: helloName).toService))
}