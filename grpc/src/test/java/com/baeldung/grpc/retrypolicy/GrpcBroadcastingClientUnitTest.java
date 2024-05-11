package com.baeldung.grpc.retrypolicy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.grpc.retry.NotificationResponse;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;

public class GrpcBroadcastingClientUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(GrpcBroadcastingClientUnitTest.class);
    ManagedChannel managedChannel;
    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @BeforeEach
    public void setup() throws Exception {
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(InProcessServerBuilder.forName(serverName)
            .directExecutor()
            .addService(new NotificationServiceImpl())
            .build()
            .start());
        managedChannel = grpcCleanup.register(InProcessChannelBuilder.forName(serverName)
            .disableServiceConfigLookUp()
            .enableRetry()
            .defaultServiceConfig(GrpcBroadcastingClient.getServiceConfig())
            .directExecutor()
            .build());
    }

    @Test
    void whenMessageBroadCasting_thenSuccessOrThrowsStatusRuntimeException() {
        try {
            NotificationResponse notificationResponse = GrpcBroadcastingClient.sendNotification(managedChannel);
            assertEquals("Message received: Warning - Heavy rains expected", notificationResponse.getResponse());
            logger.info("Message broadcast successful");
        } catch (Exception ex) {
            logger.error("Error occurred");
            assertInstanceOf(StatusRuntimeException.class, ex);
        }
    }

}
