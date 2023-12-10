package com.baeldung.harperdb;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

public class HarperDBContainer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HarperDBContainer.class);

    private static final Map<String, String> DEFAULT_ENV_MAP = Map.of("HDB_ADMIN_USERNAME", "admin", "HDB_ADMIN_PASSWORD", "password",
        "OPERATIONSAPI_NETWORK_PORT", "9925", "ROOTPATH", "/home/harperdb/hdb", "LOGGING_STDSTREAMS", "true");

    private static final Integer[] DEFAULT_EXPOSED_PORTS = { 9925, 9926 };

    private static final String HARPER_DOCKER_IMAGE = "harperdb/harperdb:latest";

    public void stop() {
        harperDBContainer.stop();
    }

    GenericContainer harperDBContainer;

    public GenericContainer installHarperDB() {
        harperDBContainer = new GenericContainer(HARPER_DOCKER_IMAGE).withEnv(DEFAULT_ENV_MAP)
            .withExposedPorts(DEFAULT_EXPOSED_PORTS);
        return harperDBContainer;
    }

    public GenericContainer installHarperDB(String dockerImgage, final Map<String, String> envMap, final Integer... exposedPorts) {
        return new GenericContainer(dockerImgage).withEnv(envMap)
            .withExposedPorts(exposedPorts);
    }
}
