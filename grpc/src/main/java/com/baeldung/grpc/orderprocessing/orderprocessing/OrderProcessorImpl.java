package com.baeldung.grpc.orderprocessing.orderprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.grpc.orderprocessing.OrderProcessorGrpc;
import com.baeldung.grpc.orderprocessing.OrderRequest;
import com.baeldung.grpc.orderprocessing.OrderResponse;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

public class OrderProcessorImpl extends OrderProcessorGrpc.OrderProcessorImplBase {
    private static final Logger logger = LoggerFactory.getLogger(OrderProcessorImpl.class);

    @Override
    public void createOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        if (!validateOrder(request)) {
             throw new StatusRuntimeException(Status.FAILED_PRECONDITION.withDescription("Order Validation failed"));
        } else {
            OrderResponse orderResponse = processOrder(request);

            responseObserver.onNext(orderResponse);
            responseObserver.onCompleted();
        }
    }

    private boolean validateOrder(OrderRequest request) {
        //forcing an exception
        int tax = 100/0;
        return false;
    }

    private OrderResponse processOrder(OrderRequest request) {
        logger.info("place the order with quantity:" + request.getQuantity());
        return OrderResponse.newBuilder()
            .setOrderID("ORD-5566")
            .setResponse("Order placed successfully")
            .build();
    }
}
