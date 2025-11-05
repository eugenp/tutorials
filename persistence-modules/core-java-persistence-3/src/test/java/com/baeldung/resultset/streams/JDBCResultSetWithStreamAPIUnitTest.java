package com.baeldung.resultset.streams;

import com.baeldung.resultset.streams.model.CityRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class JDBCResultSetWithStreamAPIUnitTest {
    private static Connection connection = null;
    private static final String JDBC_URL = "jdbc:h2:mem:testDatabase";
    private static final String USERNAME = "dbUser";
    private static final String PASSWORD = "dbPassword";

    JDBCStreamAPIRepository jdbcStreamAPIRepository = new JDBCStreamAPIRepository();

    @BeforeEach
    void setup() throws Exception {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        initialDataSetup();
    }

    private void initialDataSetup() throws SQLException {
        Statement statement = connection.createStatement();
        String ddlQuery = "CREATE TABLE cities (name VARCHAR(50), country VARCHAR(50))";
        statement.execute(ddlQuery);

        List<String> sqlQueryList = Arrays.asList(
          "INSERT INTO cities VALUES ('London', 'United Kingdom')",
          "INSERT INTO cities VALUES ('Sydney', 'Australia')",
          "INSERT INTO cities VALUES ('Bucharest', 'Romania')"
        );

        for (String query : sqlQueryList) {
            statement.execute(query);
        }
    }

    @Test
    void givenJDBCStreamAPIRepository_whenGetCitiesStreamUsingSpliterator_thenExpectedRecordsShouldBeReturned() throws SQLException {

        Stream<CityRecord> cityRecords = jdbcStreamAPIRepository
          .getCitiesStreamUsingSpliterator(connection);
        List<CityRecord> cities = cityRecords.toList();
        cityRecords.close();

        assertThat(cities)
          .containsExactly(
            new CityRecord("London", "United Kingdom"),
            new CityRecord("Sydney", "Australia"),
            new CityRecord("Bucharest", "Romania"));
    }

    @Test
    void givenJDBCStreamAPIRepository_whenGetCitiesStreamUsingJOOQ_thenExpectedRecordsShouldBeReturned() throws SQLException {

        Stream<CityRecord> cityRecords = jdbcStreamAPIRepository
          .getCitiesStreamUsingJOOQ(connection);
        List<CityRecord> cities = cityRecords.toList();
        cityRecords.close();

        assertThat(cities)
          .containsExactly(
            new CityRecord("London", "United Kingdom"),
            new CityRecord("Sydney", "Australia"),
            new CityRecord("Bucharest", "Romania"));
    }

    @Test
    void givenJDBCStreamAPIRepository_whenGetCitiesStreamUsingJdbcStream_thenExpectedRecordsShouldBeReturned() throws SQLException {

        Stream<CityRecord> cityRecords = jdbcStreamAPIRepository
          .getCitiesStreamUsingJdbcStream(connection);
        List<CityRecord> cities = cityRecords.toList();
        cityRecords.close();

        assertThat(cities)
        .containsExactly(
          new CityRecord("London", "United Kingdom"),
          new CityRecord("Sydney", "Australia"),
          new CityRecord("Bucharest", "Romania"));
    }
}
