package com.baeldung.statmentVsPreparedstatment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StatementPersonDao {

    private final Connection connection;

    public StatementPersonDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<PersonEntity> getById(int id) throws SQLException {
        String query = "SELECT id, name, FROM persons WHERE id = '" + id + "'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.first()) {
            PersonEntity result = new PersonEntity(resultSet.getInt("id"),
                resultSet.getString("name"));
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    public void insert(PersonEntity personEntity) throws SQLException {
        String query = "INSERT INTO persons(id, name) VALUES(" + personEntity.getId() + ", '"
            + personEntity.getName() + "')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void insert(List<PersonEntity> personEntities) throws SQLException {
        for (PersonEntity personEntity : personEntities) {
            insert(personEntity);
        }
    }

    public void update(PersonEntity personEntity) throws SQLException {

        String query = "UPDATE persons SET name = '" + personEntity.getName() + "' WHERE id = "
            + personEntity.getId();

        Statement statement = connection.createStatement();
        statement.executeUpdate(query);

    }

    public void deleteById(int id) throws SQLException {
        String query = "DELETE FROM persons WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public List<PersonEntity> getAll() throws SQLException {
        String query = "SELECT id, name, FROM persons";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<PersonEntity> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new PersonEntity(resultSet.getInt("id"), resultSet.getString("name")));
        }
        return result;
    }
}
