package com.baeldung.helloworld.consumer;

import com.baeldung.helloworld.stubs.HelloWorldRequest;
import com.baeldung.helloworld.stubs.HelloWorldResponse;
import com.baeldung.helloworld.stubs.HelloWorldServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class HelloWorldClient {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldClient.class);

    // constructor injection not possible
    // "Do not use in conjunction with @Autowired or @Inject"
    // (see https://github.com/grpc-ecosystem/grpc-spring)
    @GrpcClient("hello")
    HelloWorldServiceGrpc.HelloWorldServiceStub stub;

    @EventListener(ContextRefreshedEvent.class)
    void sendRequests() {
        // prepare the response callback
        final StreamObserver<HelloWorldResponse> responseObserver = new StreamObserver<HelloWorldResponse>() {
            @Override
            public void onNext(HelloWorldResponse helloWorldResponse) {
                log.info("Hello World Response: {}", helloWorldResponse.getGreeting());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Error occured", throwable);
            }

            @Override
            public void onCompleted() {
                log.info("Hello World request finished");
            }
        };
        // connect to the server
        final StreamObserver<HelloWorldRequest> request = stub.sayHello(responseObserver);
        // send multiple requests (streaming)
        Stream.of("Tom", "Julia", "Baeldung", "", "Ralf")
                .map(HelloWorldRequest.newBuilder()::setName)
                .map(HelloWorldRequest.Builder::build)
                .forEach(request::onNext);
        request.onCompleted();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
