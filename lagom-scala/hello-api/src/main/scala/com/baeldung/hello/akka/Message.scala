package com.baeldung.hello.akka

import play.api.libs.json.{Format, Json}

case class Job(jobId: String, task: String, payload: String)

object Job {
  implicit val format: Format[Job] = Json.format
}

case class JobAccepted(jobId: String)

object JobAccepted {
  implicit val format: Format[JobAccepted] = Json.format
}

case class JobStatus(jobId: String, jobStatus: String)

object JobStatus {
  implicit val format: Format[JobStatus] = Json.format
}
