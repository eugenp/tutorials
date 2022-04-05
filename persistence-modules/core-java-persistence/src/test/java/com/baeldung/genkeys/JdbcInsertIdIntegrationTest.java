package com.baeldung.genkeys;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcInsertIdIntegrationTest {

    private static final String QUERY = "insert into persons (name) values (?)";

    private static Connection connection;

    @BeforeClass
    public static void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:generated-keys", "sa", "");
        connection
          .createStatement()
          .execute("create table persons(id bigint auto_increment, name varchar(255))");
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection
          .createStatement()
          .execute("drop table persons");
        connection.close();
    }

    @Test
    public void givenInsert_whenUsingAutoGenFlag_thenBeAbleToFetchTheIdAfterward() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, "Foo");
            int affectedRows = statement.executeUpdate();
            assertThat(affectedRows).isPositive();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                assertThat(keys.next()).isTrue();
                assertThat(keys.getLong(1)).isGreaterThanOrEqualTo(1);
            }
        }
    }

    @Test
    public void givenInsert_whenUsingAutoGenFlagViaExecute_thenBeAbleToFetchTheIdAfterward() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String query = "insert into persons (name) values ('Foo')";
            int affectedRows = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            assertThat(affectedRows).isPositive();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                assertThat(keys.next()).isTrue();
                assertThat(keys.getLong(1)).isGreaterThanOrEqualTo(1);
            }
        }
    }

    @Test
    public void givenInsert_whenUsingReturningCols_thenBeAbleToFetchTheIdAfterward() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QUERY, new String[] { "id" })) {
            statement.setString(1, "Foo");
            int affectedRows = statement.executeUpdate();
            assertThat(affectedRows).isPositive();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                assertThat(keys.next()).isTrue();
                assertThat(keys.getLong(1)).isGreaterThanOrEqualTo(1);
            }
        }
    }

    @Test
    public void givenInsert_whenUsingReturningColsViaExecute_thenBeAbleToFetchTheIdAfterward() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String query = "insert into persons (name) values ('Foo')";
            int affectedRows = statement.executeUpdate(query, new String[] { "id" });
            assertThat(affectedRows).isPositive();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                assertThat(keys.next()).isTrue();
                assertThat(keys.getLong(1)).isGreaterThanOrEqualTo(1);
            }
        }
    }
}
