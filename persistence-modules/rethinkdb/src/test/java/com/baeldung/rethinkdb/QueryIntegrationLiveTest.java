package com.baeldung.rethinkdb;

import com.rethinkdb.net.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rethinkdb.RethinkDB.r;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Some tests demonstrating querying data.
 */
public class QueryIntegrationLiveTest extends TestBase {
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
    public void listAll() {
        Result<Map> results = r.db(DB_NAME).table(tableName).run(conn, Map.class);

        // We can't ensure the order the results come back in.
        Set<String> expected = new HashSet<>(Set.of(
                "String Interpolation in Java",
                "Access HTTPS REST Service Using Spring RestTemplate"
        ));

        for (Map result : results) {
            assertTrue(expected.remove(result.get("name")));
        }

        assertTrue(expected.isEmpty());
    }

    @Test
    public void listSome() {
        Result<Map> results = r.db(DB_NAME)
            .table(tableName)
            .filter(r -> r.g("name").eq("String Interpolation in Java"))
            .run(conn, Map.class);

        // We can't ensure the order the results come back in.
        Set<String> expected = Set.of("https://www.baeldung.com/java-string-interpolation");

        assertEquals(expected, results.stream()
            .map(r -> r.get("url"))
            .collect(Collectors.toSet()));
    }

    @Test
    public void getByKey() {
        Result<Map> results = r.db(DB_NAME).table(tableName).get("article1").run(conn, Map.class);

        assertEquals("String Interpolation in Java", results.first().get("name"));
    }
}
