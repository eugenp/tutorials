package com.baeldung.dapr.pubsub.publisher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.Network.NetworkImpl;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import io.dapr.spring.boot.autoconfigure.pubsub.DaprPubSubProperties;
import io.dapr.testcontainers.Component;
import io.dapr.testcontainers.DaprContainer;
import io.dapr.testcontainers.DaprLogLevel;

@TestConfiguration(proxyBeanMethods = false)
@EnableConfigurationProperties({ DaprPubSubProperties.class })
public class DaprTestContainersConfig {

    private static final Logger logger = LoggerFactory.getLogger(DaprTestContainersConfig.class);
    private static final String SHARED_NETWORK = "dapr-network";

    @Value("${server.port}")
    int serverPort;

    @Value("${spring.application.name}")
    String applicationName;

    @Bean
    public Network daprNetwork(Environment env) {
        List<com.github.dockerjava.api.model.Network> networks = DockerClientFactory.instance()
            .client()
            .listNetworksCmd()
            .withNameFilter(SHARED_NETWORK)
            .exec();
        if (networks.isEmpty()) {
            logger.info("[bael] creating reusable network...");
            NetworkImpl network = Network.builder()
                .createNetworkCmdModifier(cmd -> cmd.withName(SHARED_NETWORK))
                .build();
            String id = network.getId();
            logger.info("[bael] created network {}", id);
            return network;
        } else {
            logger.info("[bael] reusing network {}", SHARED_NETWORK);
            return new Network() {
                @Override
                public String getId() {
                    return SHARED_NETWORK;
                }

                @Override
                public Statement apply(Statement base, Description description) {
                    return base;
                }

                @Override
                public void close() {
                    // no-op
                }
            };
        }
    }

    @Bean
    public RabbitMQContainer rabbitMQContainer(Network daprNetwork, Environment env) {
        boolean reuse = env.getProperty("reuse", Boolean.class, false);

        return new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine")).withExposedPorts(5672)
            .withNetworkAliases("rabbitmq")
            .withReuse(reuse)
            .withNetwork(daprNetwork);
    }

    @Bean
    @ServiceConnection
    public DaprContainer daprContainer(Network daprNetwork, RabbitMQContainer rabbitMQ, Environment env, DaprPubSubProperties pubSub) {
        boolean reuse = env.getProperty("reuse", Boolean.class, false);

        Map<String, String> rabbitMqConfig = new HashMap<>();
        rabbitMqConfig.put("connectionString", "amqp://guest:guest@rabbitmq:5672");
        rabbitMqConfig.put("user", "guest");
        rabbitMqConfig.put("password", "guest");
        rabbitMqConfig.put("requeueInFailure", "true");

        return new DaprContainer("daprio/daprd:1.14.4").withAppName(applicationName)
            .withNetwork(daprNetwork)
            .withComponent(new Component(pubSub.getName(), "pubsub.rabbitmq", "v1", rabbitMqConfig))
            .withDaprLogLevel(DaprLogLevel.INFO)
            .withLogConsumer(outputFrame -> logger.info(outputFrame.getUtf8String()))
            .withAppPort(serverPort)
            .withAppChannelAddress("host.testcontainers.internal")
            .withReusablePlacement(reuse)
            .withAppHealthCheckPath("/actuator/health")
            .dependsOn(rabbitMQ);
    }
}
