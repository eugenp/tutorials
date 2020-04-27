package com.baeldung.hello.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.baeldung.hello.akka.{Job, JobAccepted, JobStatus}
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceAcl, ServiceCall}

trait HelloService extends Service {

  def submit(): ServiceCall[Job, JobAccepted]

  def status(): ServiceCall[NotUsed, Source[JobStatus, NotUsed]]

  override final def descriptor: Descriptor = {
    import Service._
    named("hello")
      .withCalls(
        pathCall("/api/submit", submit _),
        pathCall("/api/status", status _)
      )
      .withAutoAcl(true)
      .withAcls(
        ServiceAcl(pathRegex = Some("/api/play"))
      )
  }
}
