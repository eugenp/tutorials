package com.baeldung.hello.impl

import akka.NotUsed
import akka.actor.ActorSystem
import akka.cluster.Cluster
import akka.cluster.routing.{ClusterRouterGroup, ClusterRouterGroupSettings}
import akka.pattern.ask
import akka.routing.ConsistentHashingGroup
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

  if (Cluster.get(system).selfRoles("worker-node")) {
    system.actorOf(Worker.props(pubSub), "worker")
  }

  val workerRouter = {
    val paths = List("/user/worker")
    val groupConf = ConsistentHashingGroup(paths, hashMapping = {
      case Job(_, task, _) => task
    })
    val routerProps = ClusterRouterGroup(
      groupConf,
      ClusterRouterGroupSettings(
        totalInstances = 1000,
        routeesPaths = paths,
        allowLocalRoutees = true,
        useRoles = Set("worker-node")
      )
    ).props
    system.actorOf(routerProps, "workerRouter")
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
