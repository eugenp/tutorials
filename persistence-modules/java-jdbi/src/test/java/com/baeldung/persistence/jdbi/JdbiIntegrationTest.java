package com.baeldung.persistence.jdbi;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.result.ResultBearing;
import org.jdbi.v3.core.result.ResultProducer;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.statement.Update;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class JdbiIntegrationTest {

    @Test
    public void whenJdbiCreated_thenSuccess() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");

        Jdbi.create("WRONG");
    }

    @Test
    public void whenJdbiWithProperties_thenSuccess() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.open().close();
        Properties properties = new Properties();
        properties.setProperty("username", "sa");
        properties.setProperty("password", "");
        jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", properties);
        jdbi.open().close();
    }

    @Test
    public void whenHandle_thenBoh() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        final Handle[] handleRef = new Handle[1];
        boolean closed = jdbi.withHandle(handle -> {
            handleRef[0] = handle;
            return handle.isClosed();
        });

        assertFalse(closed);
        assertTrue(handleRef[0].isClosed());
    }

    @Test
    public void whenTableCreated_thenInsertIsPossible() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            int updateCount = handle.execute("create table PROJECT_1 (id integer identity, name varchar(50), url varchar(100))");

            assertEquals(0, updateCount);

            updateCount = handle.execute("INSERT INTO PROJECT_1 VALUES (1, 'tutorials', 'github.com/eugenp/tutorials')");

            assertEquals(1, updateCount);
        });
    }

    @Test
    public void whenIdentityColumn_thenInsertReturnsNewId() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.execute("create table PROJECT_2 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");
            Update update = handle.createUpdate(
                    "INSERT INTO PROJECT_2 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')");
            ResultBearing generatedKeys = update.executeAndReturnGeneratedKeys();

            assertEquals(0, generatedKeys.mapToMap().findOnly().get("id"));

            update = handle.createUpdate(
                    "INSERT INTO PROJECT_2 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')");

            assertEquals(1, generatedKeys.mapToMap().findOnly().get("id"));
        });
    }

    @Test
    public void whenSelectMapToMap_thenResultsAreMapEntries() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.execute("create table PROJECT_3 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");
            handle.execute("INSERT INTO PROJECT_3 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')");
            handle.execute("INSERT INTO PROJECT_3 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')");
            Query query = handle.createQuery("select * from PROJECT_3 order by id");
            List<Map<String, Object>> list = query.mapToMap().list();

            assertEquals("tutorials", list.get(0).get("name"));
            assertEquals("REST with Spring", list.get(1).get("name"));
        });
    }

    @Test
    public void whenSelectMapToString_thenResultIsString() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.execute("create table PROJECT_4 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");
            handle.execute("INSERT INTO PROJECT_4 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')");
            handle.execute("INSERT INTO PROJECT_4 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')");
            Query query = handle.createQuery("select name, url from PROJECT_4 order by id");
            List<String> list = query.mapTo(String.class).list();

            assertEquals("tutorials", list.get(0));
            assertEquals("REST with Spring", list.get(1));
        });
    }

    @Test
    public void whenSelectMapToString_thenStream() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.execute("create table PROJECT_5 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");
            handle.execute("INSERT INTO PROJECT_5 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')");
            handle.execute("INSERT INTO PROJECT_5 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')");
            Query query = handle.createQuery("select name, url from PROJECT_5 order by id");
            query.mapTo(String.class).useStream((Stream<String> stream) -> assertEquals("tutorials", stream.findFirst().get()));
        });
    }

    @Test
    public void whenNoResults_thenFindFirstReturnsNone() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.execute("create table PROJECT_6 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");

            assertFalse(handle.createQuery("select * from project_6").mapToMap().findFirst().isPresent());
        });
    }

    @Test
    public void whenMultipleResults_thenFindFirstReturnsFirst() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.execute("create table PROJECT_7 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");
            handle.execute("INSERT INTO PROJECT_7 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')");
            handle.execute("INSERT INTO PROJECT_7 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')");

            Query query = handle.createQuery("select * from project_7 order by id");
            Optional<Map<String, Object>> first = query.mapToMap().findFirst();

            assertTrue(first.isPresent());
            assertEquals("tutorials", first.get().get("name"));
        });
    }

    @Test
    public void whenNoResults_thenFindOnlyThrows() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.execute("create table PROJECT_8 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");

            try {
                handle.createQuery("select * from project_8").mapToMap().findOnly();

                fail("Exception expected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void whenMultipleResults_thenFindOnlyThrows() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.execute("create table PROJECT_9 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");
            handle.execute("INSERT INTO PROJECT_9 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')");
            handle.execute("INSERT INTO PROJECT_9 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')");

            try {
                Query query = handle.createQuery("select * from project_9");
                Map<String, Object> onlyResult = query.mapToMap().findOnly();

                fail("Exception expected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void whenParameters_thenReplacement() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.execute("create table PROJECT_10 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");
            Update update1 = handle.createUpdate("INSERT INTO PROJECT_10 (NAME, URL) VALUES (?, ?)");
            update1.bind(0, "tutorials");
            update1.bind(1, "github.com/eugenp/tutorials");
            int rows = update1.execute();

            assertEquals(1, rows);

            Update update2 = handle.createUpdate("INSERT INTO PROJECT_10 (NAME, URL) VALUES (:name, :url)");
            update2.bind("name", "REST with Spring");
            update2.bind("url", "github.com/eugenp/REST-With-Spring");
            rows = update2.execute();

            assertEquals(1, rows);

            List<Map<String, Object>> list =
                    handle.select("SELECT * FROM PROJECT_10 WHERE NAME = 'tutorials'").mapToMap().list();

            assertEquals(1, list.size());

            list = handle.select("SELECT * FROM PROJECT_10 WHERE NAME = 'REST with Spring'").mapToMap().list();

            assertEquals(1, list.size());
        });
    }

    @Test
    public void whenMultipleParameters_thenReplacement() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.execute("create table PROJECT_11 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");
            Update update = handle.createUpdate("INSERT INTO PROJECT_11 (NAME, URL) VALUES (:name, :url)");
            Map<String, String> params = new HashMap<>();
            params.put("name", "REST with Spring");
            params.put("url", "github.com/eugenp/REST-With-Spring");
            update.bindMap(params);
            int rows = update.execute();

            assertEquals(1, rows);

            List<Map<String, Object>> list =
                    handle.select("SELECT * FROM PROJECT_11").mapToMap().list();

            assertEquals(1, list.size());

            class Params {
                private String name;
                private String url;

                public Params(String name, String url) {
                    this.name = name;
                    this.url = url;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            update.bindBean(new Params("tutorials", "github.com/eugenp/tutorials"));
            rows = update.execute();

            assertEquals(1, rows);

            list = handle.select("SELECT * FROM PROJECT_11").mapToMap().list();

            assertEquals(2, list.size());
        });
    }

    @Test
    public void whenTransactionRollback_thenNoDataInserted() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.useTransaction(h -> {
                handle.execute("create table PROJECT_12 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");
                handle.execute("INSERT INTO PROJECT_12 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')");
                handle.execute("INSERT INTO PROJECT_12 (NAME, URL) VALUES ('REST with Spring', 'https://github.com/eugenp/REST-With-Spring')");
                handle.rollback();
                List<Map<String, Object>> list = handle.select("SELECT * FROM PROJECT_12").mapToMap().list();

                assertEquals(0, list.size());
            });
        });
    }

    @Test
    public void whenTransactionRollbackThenCommit_thenOnlyLastInserted() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            handle.useTransaction((Handle h) -> {
                assertEquals(handle, h);

                handle.execute("create table PROJECT_13 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");
                handle.execute("INSERT INTO PROJECT_13 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')");
                handle.rollback();
                handle.begin();
                handle.execute("INSERT INTO PROJECT_13 (NAME, URL) VALUES ('REST with Spring', 'https://github.com/eugenp/REST-With-Spring')");
                handle.rollback();
                handle.begin();
                handle.execute("INSERT INTO PROJECT_13 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')");
                handle.execute("INSERT INTO PROJECT_13 (NAME, URL) VALUES ('REST with Spring', 'https://github.com/eugenp/REST-With-Spring')");
                handle.commit();
            });
            List<Map<String, Object>> list = handle.select("SELECT * FROM PROJECT_13").mapToMap().list();

            assertEquals(2, list.size());
        });
    }


    @Test
    public void whenException_thenTransactionIsRolledBack() {
        Jdbi jdbi = Jdbi.create("jdbc:hsqldb:mem:testDB", "sa", "");
        jdbi.useHandle(handle -> {
            try {
                handle.useTransaction(h -> {
                    h.execute("create table PROJECT_14 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))");
                    h.execute("INSERT INTO PROJECT_14 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')");
                    List<Map<String, Object>> list = handle.select("SELECT * FROM PROJECT_14").mapToMap().list();

                    assertTrue(h.isInTransaction());
                    assertEquals(1, list.size());

                    throw new Exception("rollback");
                });
            } catch (Exception ignored) {}
            List<Map<String, Object>> list = handle.select("SELECT * FROM PROJECT_14").mapToMap().list();

            assertFalse(handle.isInTransaction());
            assertEquals(0, list.size());
        });
    }

}
