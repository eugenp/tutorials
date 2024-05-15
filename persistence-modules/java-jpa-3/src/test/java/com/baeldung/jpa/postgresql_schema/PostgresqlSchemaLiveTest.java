package com.baeldung.jpa.postgresql_schema;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostgresqlSchemaLiveTest {
    //This tests require running Docker application with working internet connection to fetch
    //Postgres image if the image is not available locally.

    @ClassRule
    public static PostgresqlTestContainer container = PostgresqlTestContainer.getInstance();


    @BeforeClass
    public static void setup() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("user", container.getUsername());
        properties.setProperty("password", container.getPassword());
        Connection connection = DriverManager.getConnection(container.getJdbcUrl(), properties);
        connection.createStatement().execute("CREATE SCHEMA store");
        connection.createStatement().execute("CREATE TABLE store.product(id SERIAL PRIMARY KEY, name VARCHAR(20))");
        connection.createStatement().execute("INSERT INTO store.product VALUES(1, 'test product')");
    }

    @Test
    public void settingUpSchemaUsingJdbcURL() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("user", container.getUsername());
        properties.setProperty("password", container.getPassword());
        Connection connection = DriverManager.getConnection(container.getJdbcUrl().concat("&currentSchema=store"), properties);

        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM product");
        resultSet.next();

        assertThat(resultSet.getInt(1), equalTo(1));
        assertThat(resultSet.getString(2), equalTo("test product"));
    }

    @Test
    public void settingUpSchemaUsingPGSimpleDataSource() throws Exception {
        int port = Integer.parseInt(container.getJdbcUrl().substring(container.getJdbcUrl().lastIndexOf(":") + 1, container.getJdbcUrl().lastIndexOf("/")));
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{container.getHost()});
        ds.setPortNumbers(new int[]{port});
        ds.setUser(container.getUsername());
        ds.setPassword(container.getPassword());
        ds.setDatabaseName("test");
        ds.setCurrentSchema("store");

        ResultSet resultSet = ds.getConnection().createStatement().executeQuery("SELECT * FROM product");
        resultSet.next();

        assertThat(resultSet.getInt(1), equalTo(1));
        assertThat(resultSet.getString(2), equalTo("test product"));
    }

    @Test
    public void settingUpSchemaUsingTableAnnotation() {
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.connection.url", container.getJdbcUrl());
        props.put("hibernate.connection.user", container.getUsername());
        props.put("hibernate.connection.password", container.getPassword());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql_schema_unit", props);
        EntityManager entityManager = emf.createEntityManager();

        Product product = entityManager.find(Product.class, 1);

        assertThat(product.getName(), equalTo("test product"));
    }
}
