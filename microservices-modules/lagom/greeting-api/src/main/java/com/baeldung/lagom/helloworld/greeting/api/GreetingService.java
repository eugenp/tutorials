package com.baeldung.lagom.helloworld.greeting.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import akka.NotUsed;

public interface GreetingService extends Service {

    public ServiceCall<NotUsed, String> handleGreetFrom(String user);

    @Override
    default Descriptor descriptor() {
      return named("greetingservice").withCalls(
            restCall(Method.GET, "/api/greeting/:fromUser", this::handleGreetFrom)
        ).withAutoAcl(true);
    }
}