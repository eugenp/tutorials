package com.baeldung.grpc.orderprocessing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.grpc.orderprocessing.orderprocessing.GlobalExceptionInterceptor;
import com.baeldung.grpc.orderprocessing.orderprocessing.OrderProcessorImpl;

import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;

public class OrderProcessingUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingUnitTest.class);
    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();
    OrderProcessorGrpc.OrderProcessorBlockingStub orderProcessorBlockingStub;

    @BeforeEach
    public void setup() throws Exception {

        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(InProcessServerBuilder.forName(serverName)
            .directExecutor()
            .addService(new OrderProcessorImpl())
            .intercept(new GlobalExceptionInterceptor())
            .build()
            .start());

        orderProcessorBlockingStub = OrderProcessorGrpc.newBlockingStub(grpcCleanup.register(
            InProcessChannelBuilder.forName(serverName)
                .directExecutor()
                .build()));
    }

    @Test
    void whenRuntimeExceptionInRPCEndpoint_thenHandleException() {
        OrderRequest orderRequest = OrderRequest.newBuilder()
            .setProduct("PRD-7788")
            .setQuantity(1)
            .setPrice(5000)
            .build();

        try {
            OrderResponse response = orderProcessorBlockingStub.createOrder(orderRequest);
        } catch (StatusRuntimeException ex) {
            assertTrue(ex.getStatus()
                .getDescription()
                .contains("Ticket raised:TKT"));
            logger.error("Error: {}", ex.getStatus().getDescription());
        }
        logger.info("order processing over");
    }
}

