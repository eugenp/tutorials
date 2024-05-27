package com.baeldung.grpc.alts.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.grpc.alts.AuthorizationUtil;

public class ClientAuthInterceptor implements ServerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ClientAuthInterceptor.class);
    String clientServiceAccount;
    public ClientAuthInterceptor(String clientServiceAccount) {
        this.clientServiceAccount = clientServiceAccount;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
        ServerCallHandler<ReqT, RespT> serverCallHandler) {
        logger.info("Entering interceptor {}", "ClientAuthInterceptor");
        Status status = AuthorizationUtil.clientAuthorizationCheck(serverCall,
            Lists.newArrayList(this.clientServiceAccount));

        if (!status.isOk()) {
            logger.info("Authentication of the client failed");
            serverCall.close(status, new Metadata());
        } else {
            logger.info("Client successfully authenticated");
        }
        return serverCallHandler.startCall(serverCall, metadata);
    }
}
