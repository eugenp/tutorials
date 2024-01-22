package com.baeldung.setenvironment;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

class SettingTestcontainerVariableUnitTest {

    public static final String CONTAINER_REPORT_PATH = "/app/target/surefire-reports/TEST-com.baeldung.setenvironment.SettingDockerEnvironmentVariableUnitTest.xml";
    public static final String HOST_REPORT_PATH = "./container-test-report.xml";
    public static final String DOCKERFILE = "./Dockerfile";

    @Test
    void givenTestcontainerEnvironment_whenGetEnvironmentVariable_thenReturnsCorrectValue() {
        Path dockerfilePath = Paths.get(DOCKERFILE);
        GenericContainer container = new GenericContainer(
          new ImageFromDockerfile().withDockerfile(dockerfilePath));
        assertThat(container).isNotNull();
        container.start();
        while (container.isRunning()) {
            // Busy spin
        }
        container.copyFileFromContainer(CONTAINER_REPORT_PATH, HOST_REPORT_PATH);
    }
}
