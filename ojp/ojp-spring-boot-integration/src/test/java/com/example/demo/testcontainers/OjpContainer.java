package com.example.demo.testcontainers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

/**
 * Custom TestContainer for OJP (Open JDBC Proxy).
 * This container wraps the rrobetti/ojp Docker image to provide
 * database connection pooling and monitoring capabilities.
 */
public class OjpContainer extends GenericContainer<OjpContainer> {

    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse("rrobetti/ojp:0.3.1-beta");
    private static final int OJP_PORT = 1059;

    public OjpContainer() {
        this(DEFAULT_IMAGE_NAME);
    }

    public OjpContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        dockerImageName.assertCompatibleWith(DEFAULT_IMAGE_NAME);
        
        // Expose the OJP port
        addExposedPorts(OJP_PORT);
        
        // Wait for the container to be ready
        // OJP should start and be ready to accept connections
        waitingFor(Wait.forListeningPort());
    }

    /**
     * Get the host and port to connect to OJP
     * @return Connection string in format "host:port"
     */
    public String getOjpConnectionString() {
        return getHost() + ":" + getMappedPort(OJP_PORT);
    }

    /**
     * Get the mapped port for OJP
     * @return The mapped port number
     */
    public Integer getOjpPort() {
        return getMappedPort(OJP_PORT);
    }
}
