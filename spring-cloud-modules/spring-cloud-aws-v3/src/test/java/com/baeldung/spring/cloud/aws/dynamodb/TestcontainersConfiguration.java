package com.baeldung.spring.cloud.aws.dynamodb;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    LocalStackContainer localStackContainer() {
        return new LocalStackContainer(DockerImageName.parse("localstack/localstack:4.3.0"))
            .withCopyFileToContainer(
                MountableFile.forClasspathResource("init-dynamodb-table.sh", 0744),
                "/etc/localstack/init/ready.d/init-dynamodb-table.sh"
            )
            .withServices(LocalStackContainer.Service.DYNAMODB)
            .waitingFor(Wait.forLogMessage(".*Executed init-dynamodb-table.sh.*", 1));
    }

}