package com.baeldung.quarkus.testcontainers;

import java.util.Map;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class CustomerServiceTestcontainersManager implements QuarkusTestResourceLifecycleManager {

    private PostgreSQLContainer<?> postgreSQLContainer;
    private GenericContainer<?> orderService;

    @Override
    public Map<String, String> start() {
        Network network = Network.newNetwork();
        String networkAlias = "baeldung";

        postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14")).withExposedPorts(5432)
            .withDatabaseName("quarkus")
            .withUsername("quarkus")
            .withPassword("quarkus")
            .withNetwork(network)
            .withNetworkAliases(networkAlias);
        postgreSQLContainer.start();

        String jdbcUrl = String.format("jdbc:postgresql://%s:5432/quarkus", networkAlias);
        orderService = new GenericContainer<>(DockerImageName.parse("quarkus/order-service-jvm:latest")).withExposedPorts(8080)
            .withEnv("quarkus.datasource.jdbc.url", jdbcUrl)
            .withEnv("quarkus.datasource.username", postgreSQLContainer.getUsername())
            .withEnv("quarkus.datasource.password", postgreSQLContainer.getPassword())
            .withEnv("quarkus.hibernate-orm.database.generation", "drop-and-create")
            .withNetwork(network)
            .dependsOn(postgreSQLContainer)
            .waitingFor(Wait.forListeningPort());
        orderService.start();

        String orderInfoUrl = String.format("http://%s:%s/orderapi/v1", orderService.getHost(), orderService.getMappedPort(8080));
        return Map.of("quarkus.rest-client.order-api.url", orderInfoUrl);
    }

    @Override
    public void stop() {
        if (orderService != null) {
            orderService.stop();
        }
        if (postgreSQLContainer != null) {
            postgreSQLContainer.stop();
        }
    }
}
