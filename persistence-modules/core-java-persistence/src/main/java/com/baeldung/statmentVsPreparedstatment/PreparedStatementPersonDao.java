package com.baeldung.statmentVsPreparedstatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PreparedStatementPersonDao {

    private final Connection connection;

    public PreparedStatementPersonDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<PersonEntity> getById(int id) throws SQLException {
        String query = "SELECT id, name FROM persons WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.first()) {

            PersonEntity result = new PersonEntity(resultSet.getInt("id"),
                resultSet.getString("name"));

            return Optional.of(result);
        } else {
            return Optional.empty();
        }

    }

    public void insert(PersonEntity personEntity) throws SQLException {

        String query = "INSERT INTO persons(id, name) VALUES( ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, personEntity.getId());
        preparedStatement.setString(2, personEntity.getName());
        preparedStatement.executeUpdate();

    }

    public void insert(List<PersonEntity> personEntities) throws SQLException {
        String query = "INSERT INTO persons(id, name) VALUES( ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (PersonEntity personEntity : personEntities) {
            preparedStatement.setInt(1, personEntity.getId());
            preparedStatement.setString(2, personEntity.getName());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();

    }

    public void update(PersonEntity personEntity) throws SQLException {
        String query = "UPDATE persons SET name = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, personEntity.getName());
        preparedStatement.setInt(2, personEntity.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteById(int id) throws SQLException {
        String query = "DELETE FROM persons WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public List<PersonEntity> getAll() throws SQLException {
        String query = "SELECT id, name FROM persons";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<PersonEntity> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new PersonEntity(resultSet.getInt("id"), resultSet.getString("name")));
        }
        return result;
    }
}
