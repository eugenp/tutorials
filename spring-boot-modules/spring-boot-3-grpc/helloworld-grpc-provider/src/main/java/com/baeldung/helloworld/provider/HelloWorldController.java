package com.baeldung.helloworld.provider;

import com.baeldung.helloworld.stubs.HelloWorldRequest;
import com.baeldung.helloworld.stubs.HelloWorldResponse;
import com.baeldung.helloworld.stubs.HelloWorldServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@GrpcService
public class HelloWorldController extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldController.class);

    private HelloWorldResponse sayHello(HelloWorldRequest request) {
        log.info("Received request: {}", request);
        return HelloWorldResponse
                .newBuilder()
                .setGreeting("Hello "
                        + Optional
                        .of(request.getName())
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .orElse("World")
                        + "!"
                )
                .build();
    }

    @Override
    public StreamObserver<HelloWorldRequest> sayHello(
            StreamObserver<HelloWorldResponse> responseObserver
    ) {
        return StreamObserverUtility.proxyStream(
                responseObserver,
                this::sayHello
        );
    }
}
