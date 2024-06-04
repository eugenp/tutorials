package com.baeldung.grpc.repeated;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;

public class OrderService extends OrderServiceGrpc.OrderServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Override
    public void createOrder(UnPackedOrder unpackedOrder, StreamObserver<UnPackedOrder> responseObserver) {
        List<Integer> productIds = unpackedOrder.getProductIdsList();
        if(validateProducts(productIds)) {
            int orderID = insertOrder(unpackedOrder);
            logger.info("Order {} created successfully", orderID);
            UnPackedOrder createdUnPackedOrder = UnPackedOrder.newBuilder(unpackedOrder).setOrderId(orderID).build();
            responseObserver.onNext(createdUnPackedOrder);
            responseObserver.onCompleted();
        }
    }

    private int insertOrder(UnPackedOrder unpackedOrder) {
        return new Random().nextInt(100);
    }

    private boolean validateProducts(List<Integer> productIds) {
        return true;
    }
}
