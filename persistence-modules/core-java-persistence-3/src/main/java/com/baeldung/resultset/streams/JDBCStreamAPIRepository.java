package com.baeldung.resultset.streams;

import com.baeldung.resultset.streams.model.CityRecord;
import jdbc.stream.JdbcStream;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JDBCStreamAPIRepository {

    private static final String QUERY = "SELECT name, country FROM cities";
    private final Logger logger = LoggerFactory.getLogger(JDBCStreamAPIRepository.class);

    public Stream<CityRecord> getCitiesStreamUsingSpliterator(Connection connection)
            throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        connection.setAutoCommit(false);
        preparedStatement.setFetchSize(5000);
        ResultSet resultSet = preparedStatement.executeQuery();

        return StreamSupport.stream(new Spliterators.AbstractSpliterator<CityRecord>(
          Long.MAX_VALUE, Spliterator.ORDERED) {
            @Override
            public boolean tryAdvance(Consumer<? super CityRecord> action) {
                try {
                    if(!resultSet.next()) return false;
                    action.accept(createCityRecord(resultSet));
                    return true;
                } catch(SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }, false)
          .onClose(() -> closeResources(connection, resultSet, preparedStatement));
    }

    private void closeResources(Connection connection, ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            resultSet.close();
            preparedStatement.close();
            connection.close();
            logger.info("Resources closed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Stream<CityRecord> getCitiesStreamUsingJOOQ(Connection connection)
            throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        connection.setAutoCommit(false);
        preparedStatement.setFetchSize(5000);
        ResultSet resultSet = preparedStatement.executeQuery();

        return DSL.using(connection)
          .fetchStream(resultSet)
          .map(r -> new CityRecord(r.get("NAME", String.class),
            r.get("COUNTRY", String.class)))
          .onClose(() -> closeResources(connection, resultSet, preparedStatement));
    }

    public Stream<CityRecord> getCitiesStreamUsingJdbcStream(Connection connection)
            throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
        connection.setAutoCommit(false);
        preparedStatement.setFetchSize(5000);
        ResultSet resultSet = preparedStatement.executeQuery();

        return JdbcStream.stream(resultSet)
          .map(r -> {
              try {
                  return createCityRecord(resultSet);
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              }
          })
          .onClose(() -> closeResources(connection, resultSet, preparedStatement));
    }

    private CityRecord createCityRecord(ResultSet resultSet) throws SQLException {
        return new CityRecord(resultSet.getString(1), resultSet.getString(2));
    }
}
