package com.baeldung.hello.akka

import akka.actor.{Actor, Props}
import akka.event.Logging
import com.lightbend.lagom.scaladsl.pubsub.{PubSubRegistry, TopicId}

object Worker {
  def props(pubSub: PubSubRegistry) = Props(new Worker(pubSub))
}

class Worker(pubSub: PubSubRegistry) extends Actor {
  private val log = Logging.getLogger(context.system, this)

  override def receive = {
    case job@Job(id, task, payload) =>
      log.info("Working on job: {}", job)
      sender ! JobAccepted(id)
      val topic = pubSub.refFor(TopicId[JobStatus]("job-status"))
      topic.publish(JobStatus(job.jobId, "started"))
      // perform the work...
      topic.publish(JobStatus(job.jobId, "completed"))
  }
}
