package com.baeldung.hello.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.baeldung.hello.api.HelloService
import com.baeldung.hello.play.SimplePlayRouter
import com.lightbend.lagom.scaladsl.pubsub.PubSubComponents
import com.softwaremill.macwire._

class HelloLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new HelloApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new HelloApplication(context) with LagomDevModeComponents {
    }

  override def describeService = Some(readDescriptor[HelloService])
}

abstract class HelloApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with PubSubComponents
    with AhcWSComponents {

  override lazy val lagomServer: LagomServer = serverFor[HelloService](wire[HelloServiceImpl])
    .additionalRouter(wire[SimplePlayRouter].router)

}
