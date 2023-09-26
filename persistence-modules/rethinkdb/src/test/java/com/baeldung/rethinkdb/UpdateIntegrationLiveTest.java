package com.baeldung.rethinkdb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.rethinkdb.RethinkDB.r;

/**
 * Some tests demonstrating updating data.
 */
public class UpdateIntegrationLiveTest extends TestBase {
    /**
     * Create a table for the tests.
     */
    @BeforeEach
    public void createTable() {
        r.db(DB_NAME).tableCreate(tableName).run(conn);

        r.db(DB_NAME).table(tableName)
            .insert(
                r.hashMap()
                    .with("id", "article1")
                    .with("name", "String Interpolation in Java")
                    .with("url", "https://www.baeldung.com/java-string-interpolation")
            ).run(conn);
        r.db(DB_NAME).table(tableName)
            .insert(
                r.hashMap()
                    .with("id", "article2")
                    .with("name", "Access HTTPS REST Service Using Spring RestTemplate")
                    .with("url", "https://www.baeldung.com/spring-resttemplate-secure-https-service")
            ).run(conn);
    }

    @Test
    public void updateAll() {
        r.db(DB_NAME).table(tableName).update(r.hashMap().with("site", "Baeldung")).run(conn);
    }

    @Test
    public void updateSome() {
        r.db(DB_NAME).table(tableName)
            .filter(r -> r.g("name").eq("String Interpolation in Java"))
            .update(r.hashMap().with("category", "java"))
            .run(conn);
    }

    @Test
    public void delete() {
        r.db(DB_NAME).table(tableName)
            .filter(r -> r.g("name").eq("String Interpolation in Java"))
            .delete()
            .run(conn);
    }
}
