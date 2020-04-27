package com.baeldung.hello.impl

import akka.NotUsed
import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.stream.scaladsl.Source
import akka.util.Timeout
import com.baeldung.hello.akka.{Job, JobAccepted, JobStatus, Worker}
import com.baeldung.hello.api.HelloService
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.pubsub.{PubSubRegistry, TopicId}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class HelloServiceImpl(system: ActorSystem, pubSub: PubSubRegistry)(implicit ec: ExecutionContext)
  extends HelloService {

  val workerRouter = {
    system.actorOf(Worker.props(pubSub), "workerRouter")
  }

  override def submit(): ServiceCall[Job, JobAccepted] = ServiceCall {
    job =>
      //Future{JobAccepted(job.jobId)}
      implicit val timeout = Timeout(5.seconds)
      (workerRouter ? job).mapTo[JobAccepted]
  }

  override def status(): ServiceCall[NotUsed, Source[JobStatus, NotUsed]] = ServiceCall {
    _ =>
      val topic = pubSub.refFor(TopicId[JobStatus]("job-status"))
      Future.successful(topic.subscriber)
  }

}
