package com.baeldung.persistence.sql2o;

import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.ResultSetIterable;
import org.sql2o.Sql2o;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

public class Sql2oIntegrationTest {

    @Test
    public void whenSql2oCreated_thenSuccess() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            java.sql.Connection jdbcConnection = connection.getJdbcConnection();
            assertFalse(jdbcConnection.isClosed());
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void whenTableCreated_thenInsertIsPossible() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            connection.createQuery("create table PROJECT_1 (id integer identity, name varchar(50), url varchar(100))").executeUpdate();
            assertEquals(0, connection.getResult());
            connection.createQuery("INSERT INTO PROJECT_1 VALUES (1, 'tutorials', 'github.com/eugenp/tutorials')").executeUpdate();
            assertEquals(1, connection.getResult());
            connection.createQuery("drop table PROJECT_1").executeUpdate();
        }
    }

    @Test
    public void whenIdentityColumn_thenInsertReturnsNewId() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            connection.createQuery("create table PROJECT_2 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))").executeUpdate();
            Query query = connection.createQuery(
                    "INSERT INTO PROJECT_2 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')",
                    true);
            assertEquals(0, query.executeUpdate().getKey());
            query = connection.createQuery("INSERT INTO PROJECT_2 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')",
                    true);
            assertEquals(1, query.executeUpdate().getKeys()[0]);
            connection.createQuery("drop table PROJECT_2").executeUpdate();
        }
    }

    @Test
    public void whenSelect_thenResultsAreObjects() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            connection.createQuery("create table PROJECT_3 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_3 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_3 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')").executeUpdate();
            Query query = connection.createQuery("select * from PROJECT_3 order by id");
            List<Project> list = query.executeAndFetch(Project.class);

            assertEquals("tutorials", list.get(0).getName());
            assertEquals("REST with Spring", list.get(1).getName());

            connection.createQuery("drop table PROJECT_3").executeUpdate();
        }
    }

    @Test
    public void whenSelectAlias_thenResultsAreObjects() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            connection.createQuery("create table PROJECT_4 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100), creation_date date)").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_4 (NAME, URL, creation_date) VALUES ('tutorials', 'github.com/eugenp/tutorials', '2019-01-01')").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_4 (NAME, URL, creation_date) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring', '2019-02-01')").executeUpdate();
            Query query = connection.createQuery("select NAME, URL, creation_date as creationDate from PROJECT_4 order by id");
            List<Project> list = query.executeAndFetch(Project.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            assertEquals("2019-01-01", sdf.format(list.get(0).getCreationDate()));
            assertEquals("2019-02-01", sdf.format(list.get(1).getCreationDate()));

            connection.createQuery("drop table PROJECT_4").executeUpdate();
        }
    }

    @Test
    public void whenSelectMapping_thenResultsAreObjects() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            connection.createQuery("create table PROJECT_5 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100), creation_date date)").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_5 (NAME, URL, creation_date) VALUES ('tutorials', 'github.com/eugenp/tutorials', '2019-01-01')").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_5 (NAME, URL, creation_date) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring', '2019-02-01')").executeUpdate();
            Query query = connection.createQuery("select * from PROJECT_5 order by id")
                    .addColumnMapping("CrEaTiOn_date", "creationDate");
            List<Project> list = query.executeAndFetch(Project.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            assertEquals("2019-01-01", sdf.format(list.get(0).getCreationDate()));
            assertEquals("2019-02-01", sdf.format(list.get(1).getCreationDate()));

            connection.createQuery("drop table PROJECT_5").executeUpdate();
        }
    }

    @Test
    public void whenSelectCount_thenResultIsScalar() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            connection.createQuery("create table PROJECT_6 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100), creation_date date)").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_6 (NAME, URL, creation_date) VALUES ('tutorials', 'github.com/eugenp/tutorials', '2019-01-01')").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_6 (NAME, URL, creation_date) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring', '2019-02-01')").executeUpdate();
            Query query = connection.createQuery("select count(*) from PROJECT_6");
            assertEquals(2.0, query.executeScalar(Double.TYPE), 0.001);
            connection.createQuery("drop table PROJECT_6").executeUpdate();
        }
    }

    @Test
    public void whenFetchTable_thenResultsAreMaps() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            connection.createQuery("create table PROJECT_5 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100), creation_date date)").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_5 (NAME, URL, creation_date) VALUES ('tutorials', 'github.com/eugenp/tutorials', '2019-01-01')").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_5 (NAME, URL, creation_date) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring', '2019-02-01')").executeUpdate();
            Query query = connection.createQuery("select * from PROJECT_5 order by id");
            Table table = query.executeAndFetchTable();
            List<Map<String, Object>> list = table.asList();

            assertEquals("tutorials", list.get(0).get("name"));
            assertEquals("REST with Spring", list.get(1).get("name"));

            connection.createQuery("drop table PROJECT_5").executeUpdate();
        }
    }

    @Test
    public void whenFetchTable_thenResultsAreRows() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            connection.createQuery("create table PROJECT_5 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100), creation_date date)").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_5 (NAME, URL, creation_date) VALUES ('tutorials', 'github.com/eugenp/tutorials', '2019-01-01')").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_5 (NAME, URL, creation_date) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring', '2019-02-01')").executeUpdate();
            Query query = connection.createQuery("select * from PROJECT_5 order by id");
            Table table = query.executeAndFetchTable();
            List<Row> rows = table.rows();

            assertEquals("tutorials", rows.get(0).getString("name"));
            assertEquals("REST with Spring", rows.get(1).getString("name"));

            connection.createQuery("drop table PROJECT_5").executeUpdate();
        }
    }

    @Test
    public void whenParameters_thenReplacement() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            connection.createQuery("create table PROJECT_10 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))").executeUpdate();
            Query query = connection.createQuery("INSERT INTO PROJECT_10 (NAME, URL) VALUES (:name, :url)")
                    .addParameter("name", "REST with Spring")
                    .addParameter("url", "github.com/eugenp/REST-With-Spring");
            assertEquals(1, query.executeUpdate().getResult());

            List<Project> list = connection.createQuery("SELECT * FROM PROJECT_10 WHERE NAME = 'REST with Spring'").executeAndFetch(Project.class);
            assertEquals(1, list.size());

            connection.createQuery("drop table PROJECT_10").executeUpdate();
        }
    }

    @Test
    public void whenPOJOParameters_thenReplacement() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            connection.createQuery("create table PROJECT_11 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))").executeUpdate();

            Project project = new Project();
            project.setName("REST with Spring");
            project.setUrl("github.com/eugenp/REST-With-Spring");
            connection.createQuery("INSERT INTO PROJECT_11 (NAME, URL) VALUES (:name, :url)")
                    .bind(project).executeUpdate();
            assertEquals(1, connection.getResult());

            List<Project> list = connection.createQuery("SELECT * FROM PROJECT_11 WHERE NAME = 'REST with Spring'").executeAndFetch(Project.class);
            assertEquals(1, list.size());

            connection.createQuery("drop table PROJECT_11").executeUpdate();
        }
    }

    @Test
    public void whenTransactionRollback_thenNoDataInserted() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.beginTransaction()) {
            connection.createQuery("create table PROJECT_12 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_12 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_12 (NAME, URL) VALUES ('REST with Spring', 'https://github.com/eugenp/REST-With-Spring')").executeUpdate();
            connection.rollback();
            List<Map<String, Object>> list = connection.createQuery("SELECT * FROM PROJECT_12").executeAndFetchTable().asList();
            assertEquals(0, list.size());
        }
    }

    @Test
    public void whenTransactionEnds_thenSubsequentStatementsNotRolledBack() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.beginTransaction()) {
            connection.createQuery("create table PROJECT_13 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_13 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')").executeUpdate();
            List<Map<String, Object>> list = connection.createQuery("SELECT * FROM PROJECT_13").executeAndFetchTable().asList();
            assertEquals(1, list.size());
            connection.rollback();
            list = connection.createQuery("SELECT * FROM PROJECT_13").executeAndFetchTable().asList();
            assertEquals(0, list.size());
            connection.createQuery("INSERT INTO PROJECT_13 (NAME, URL) VALUES ('REST with Spring', 'https://github.com/eugenp/REST-With-Spring')").executeUpdate();
            list = connection.createQuery("SELECT * FROM PROJECT_13").executeAndFetchTable().asList();
            assertEquals(1, list.size());
            //No implicit rollback
        }
        try(Connection connection = sql2o.beginTransaction()) {
            List<Map<String, Object>> list = connection.createQuery("SELECT * FROM PROJECT_13").executeAndFetchTable().asList();
            assertEquals(1, list.size());
        }
    }

    @Test
    public void whenTransactionRollbackThenCommit_thenOnlyLastInserted() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.beginTransaction()) {
            connection.createQuery("create table PROJECT_14 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_14 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')").executeUpdate();
            List<Map<String, Object>> list = connection.createQuery("SELECT * FROM PROJECT_14").executeAndFetchTable().asList();
            assertEquals(1, list.size());
            connection.rollback(false);
            list = connection.createQuery("SELECT * FROM PROJECT_14").executeAndFetchTable().asList();
            assertEquals(0, list.size());
            connection.createQuery("INSERT INTO PROJECT_14 (NAME, URL) VALUES ('REST with Spring', 'https://github.com/eugenp/REST-With-Spring')").executeUpdate();
            list = connection.createQuery("SELECT * FROM PROJECT_14").executeAndFetchTable().asList();
            assertEquals(1, list.size());
            //Implicit rollback
        }
        try(Connection connection = sql2o.beginTransaction()) {
            List<Map<String, Object>> list = connection.createQuery("SELECT * FROM PROJECT_14").executeAndFetchTable().asList();
            assertEquals(0, list.size());
            connection.createQuery("INSERT INTO PROJECT_14 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_14 (NAME, URL) VALUES ('REST with Spring', 'https://github.com/eugenp/REST-With-Spring')").executeUpdate();
            connection.commit();
            list = connection.createQuery("SELECT * FROM PROJECT_14").executeAndFetchTable().asList();
            assertEquals(2, list.size());
        }
        try(Connection connection = sql2o.beginTransaction()) {
            List<Map<String, Object>> list = connection.createQuery("SELECT * FROM PROJECT_14").executeAndFetchTable().asList();
            assertEquals(2, list.size());
        }
    }

    @Test
    public void whenBatch_thenMultipleInserts() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.beginTransaction()) {
            connection.createQuery("create table PROJECT_15 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))").executeUpdate();
            Query query = connection.createQuery("INSERT INTO PROJECT_15 (NAME, URL) VALUES (:name, :url)");
            for(int i = 0; i < 1000; i++) {
                query.addParameter("name", "tutorials" + i);
                query.addParameter("url", "https://github.com/eugenp/tutorials" + i);
                query.addToBatch();
            }
            query.executeBatch();
            connection.commit();
        }
        try(Connection connection = sql2o.beginTransaction()) {
            assertEquals(1000L, connection.createQuery("SELECT count(*) FROM PROJECT_15").executeScalar());
        }
    }

    @Test
    public void whenLazyFetch_thenResultsAreObjects() {
        Sql2o sql2o = new Sql2o("jdbc:hsqldb:mem:testDB", "sa", "");
        try(Connection connection = sql2o.open()) {
            connection.createQuery("create table PROJECT_16 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_16 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')").executeUpdate();
            connection.createQuery("INSERT INTO PROJECT_16 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')").executeUpdate();
            Query query = connection.createQuery("select * from PROJECT_16 order by id");
            try(ResultSetIterable<Project> projects = query.executeAndFetchLazy(Project.class)) {
                for(Project p : projects) {
                    assertNotNull(p.getName());
                    assertNotNull(p.getUrl());
                    assertNull(p.getCreationDate());
                }
            }
            connection.createQuery("drop table PROJECT_16").executeUpdate();
        }
    }

    static class Project {
        private long id;
        private String name;
        private String url;
        private Date creationDate;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public Date getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(Date creationDate) {
            this.creationDate = creationDate;
        }
    }


}
