package com.baeldung.grpc.orderprocessing.orderprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

public class LogInterceptor implements ServerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
        ServerCallHandler<ReqT, RespT> next) {
        logger.info("Entering LogInterceptor");
        logMessage(serverCall);
        ServerCall.Listener<ReqT> delegate = next.startCall(serverCall, metadata);
        return delegate;
    }

    private <ReqT, RespT> void logMessage(ServerCall<ReqT, RespT> call) {
        //forcing exception
        int result = 100/0;
    }
}
