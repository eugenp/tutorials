package com.baeldung.dapr.workflow.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import java.time.Duration;

import io.dapr.spring.boot.autoconfigure.pubsub.DaprPubSubProperties;
import io.dapr.testcontainers.Component;
import io.dapr.testcontainers.DaprContainer;
import io.dapr.testcontainers.DaprLogLevel;

@TestConfiguration(proxyBeanMethods = false)
@EnableConfigurationProperties({ DaprPubSubProperties.class })
public class DaprWorkflowTestConfig {

    private static final Logger logger = LoggerFactory.getLogger(DaprWorkflowTestConfig.class);

    @Value("${server.port}")
    private int serverPort;

    @Bean
    public Network daprNetwork() {
        return Network.newNetwork();
    }

    @Bean
    public RabbitMQContainer rabbitMQContainer(Network daprNetwork) {
        return new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.8.26-management")).withExposedPorts(5672, 15672)
            .withNetworkAliases("rabbitmq")
            .withNetwork(daprNetwork)
            .waitingFor(Wait.forListeningPort()
                .withStartupTimeout(Duration.ofMinutes(2)));
    }

    @Bean
    @Qualifier("redis")
    public GenericContainer<?> redisContainer(Network daprNetwork) {
        return new GenericContainer<>(DockerImageName.parse("redis:7-alpine")).withExposedPorts(6379)
            .withNetworkAliases("redis")
            .withNetwork(daprNetwork);
    }

    @Bean
    @ServiceConnection
    public DaprContainer daprContainer(Network daprNetwork, RabbitMQContainer rabbitMQ, @Qualifier("redis") GenericContainer<?> redis, DaprPubSubProperties pubSub) {

        Map<String, String> rabbitMqConfig = new HashMap<>();
        rabbitMqConfig.put("connectionString", "amqp://guest:guest@rabbitmq:5672");
        rabbitMqConfig.put("user", "guest");
        rabbitMqConfig.put("password", "guest");

        Map<String, String> redisConfig = new HashMap<>();
        redisConfig.put("redisHost", "redis:6379");
        redisConfig.put("actorStateStore", "true");

        return new DaprContainer("daprio/daprd:1.16.0").withAppName("dapr-workflows")
            .withNetwork(daprNetwork)
            .withComponent(new Component(pubSub.getName(), "pubsub.rabbitmq", "v1", rabbitMqConfig))
            .withComponent(new Component("statestore", "state.redis", "v1", redisConfig))
            .withAppPort(serverPort)
            .withAppChannelAddress("host.testcontainers.internal")
            .withDaprLogLevel(DaprLogLevel.INFO)
            .withLogConsumer(outputFrame -> logger.info(outputFrame.getUtf8String()))
            .dependsOn(rabbitMQ, redis);
    }
}
