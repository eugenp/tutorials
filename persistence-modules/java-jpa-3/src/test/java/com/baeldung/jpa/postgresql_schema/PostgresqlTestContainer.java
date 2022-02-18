package com.baeldung.jpa.postgresql_schema;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresqlTestContainer  extends PostgreSQLContainer<PostgresqlTestContainer> {

    private static final String IMAGE_VERSION = "postgres";

    private static PostgresqlTestContainer container;


    private PostgresqlTestContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgresqlTestContainer getInstance() {
        if (container == null) {
            container = new PostgresqlTestContainer();
        }
        return container;
    }
}
