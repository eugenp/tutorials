package com.baeldung.util;

import org.testcontainers.containers.PostgreSQLContainer;

public class BaeldungPostgresqlContainer extends PostgreSQLContainer<BaeldungPostgresqlContainer> {

    private static final String IMAGE_VERSION = "postgres:11.1";

    private static BaeldungPostgresqlContainer container;


    private BaeldungPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static BaeldungPostgresqlContainer getInstance() {
        if (container == null) {
            container = new BaeldungPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
