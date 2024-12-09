package com.baeldung.automq;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    private static final String COMPOSE_URL = "https://download.automq.com/community_edition/standalone_deployment/docker-compose.yaml";

    @Bean
    public ComposeContainer composeContainer() throws IOException {
        File dockerCompose = downloadComposeFile();
        return new ComposeContainer(dockerCompose)
            .withLocalCompose(true);
    }

    @Bean
    public DynamicPropertyRegistrar dynamicPropertyRegistrar() {
        return registry -> {
            registry.add("spring.kafka.bootstrap-servers", () -> "localhost:9094,localhost:9095");
        };
    }

    private File downloadComposeFile() throws IOException {
        File dockerCompose = Files.createTempFile("docker-compose", ".yaml").toFile();
        dockerCompose.deleteOnExit();
        FileUtils.copyURLToFile(URI.create(COMPOSE_URL).toURL(), dockerCompose);
        return removeContainerNames(dockerCompose);
    }

    /**
     * The container_name property of Docker Compose is not currently supported by Testcontainers.
     * The issue can be tracked at https://github.com/testcontainers/testcontainers-java/issues/2472
     */
    private File removeContainerNames(File composeFile) throws IOException {
        List<String> filteredLines = Files.readAllLines(composeFile.toPath())
            .stream()
            .filter(line -> !line.contains("container_name:"))
            .toList();
        Files.write(composeFile.toPath(), filteredLines);
        return composeFile;
    }

}