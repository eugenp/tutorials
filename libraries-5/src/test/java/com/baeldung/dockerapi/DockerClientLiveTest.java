package com.baeldung.dockerapi;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertNotNull;

public class DockerClientLiveTest {

    @Test
    public void whenCreatingDockerClient_thenReturnDefaultInstance() {

        // when
        DefaultDockerClientConfig.Builder config = DefaultDockerClientConfig.createDefaultConfigBuilder();
        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

        // then
        assertNotNull(dockerClient);
    }

    @Test
    public void whenCreatingDockerClientWithDockerHost_thenReturnInstance() {
        // when
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://docker.bealdung.com:2375").build();

        // then
        assertNotNull(dockerClient);
    }

    @Test
    public void whenCreatingAdvanceDockerClient_thenReturnInstance() {

        // when
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().withRegistryEmail("info@bealdung.com").withRegistryUrl("register.bealdung.io/v2/").withRegistryPassword("strongpassword").withRegistryUsername("bealdung")
                .withDockerCertPath("/home/bealdung/public/.docker/certs").withDockerConfig("/home/bealdung/public/.docker/").withDockerTlsVerify("1").withDockerHost("tcp://docker.beauldung.com:2376").build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

        // then
        assertNotNull(dockerClient);
    }

    @Test
    public void whenCreatingDockerClientWithProperties_thenReturnInstance() {

        // when
        Properties properties = new Properties();
        properties.setProperty("registry.email", "info@bealdung.com");
        properties.setProperty("registry.url", "register.bealdung.io/v2/");
        properties.setProperty("registry.password", "strongpassword");
        properties.setProperty("registry.username", "bealdung");
        properties.setProperty("DOCKER_CERT_PATH", "/home/bealdung/public/.docker/certs");
        properties.setProperty("DOCKER_CONFIG", "/home/bealdung/public/.docker/");
        properties.setProperty("DOCKER_TLS_VERIFY", "1");
        properties.setProperty("DOCKER_HOST", "tcp://docker.bealdung.com:2376");

        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().withProperties(properties).build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

        // then
        assertNotNull(dockerClient);
    }
}
