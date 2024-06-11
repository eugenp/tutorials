package com.baeldung.grpc.orderprocessing.orderprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

public class GlobalExceptionInterceptor implements ServerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionInterceptor.class);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata headers,
        ServerCallHandler<ReqT, RespT> next) {
        ServerCall.Listener<ReqT> delegate = null;
        logger.info("In the interceptor, going to call the target RPC");
        try {
            delegate = next.startCall(serverCall, headers);
        } catch(Exception ex) {
            logger.error("Exception caught in a subsequent interceptor", ex.getMessage());
            return handleInterceptorException(ex, serverCall);
        }
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(delegate) {
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (Exception ex) {
                    logger.error("Exception caught in a RPC endpoint", ex.getMessage());
                    handleEndpointException(ex, serverCall);

                }
            }
        };
    }

    private static <ReqT, RespT> void handleEndpointException(Exception ex, ServerCall<ReqT, RespT> serverCall) {
        logger.error("The ex.getMessage() = {}", ex.getMessage());
        String ticket = new TicketService().createTicket(ex.getMessage());
        //send the response back
        serverCall.close(Status.INTERNAL
            .withCause(ex)
            .withDescription(ex.getMessage() + ", Ticket raised:" + ticket), new Metadata());
    }

    private <ReqT, RespT> ServerCall.Listener<ReqT> handleInterceptorException(Throwable t, ServerCall<ReqT, RespT> serverCall) {
        String ticket = new TicketService().createTicket(t.getMessage());
        serverCall.close(Status.INTERNAL
            .withCause(t)
            .withDescription("An exception occurred in a **subsequent** interceptor:" + ", Ticket raised:" + ticket), new Metadata());

        return new ServerCall.Listener<ReqT>() {
            // no-op
        };
    }
}
