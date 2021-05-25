package com.baeldung.insertnull;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class InsertNullUnitTest {

    private final String SQL = "INSERT INTO Person VALUES(?,?,?,?)";

    @Test
    public void givenNewPerson_whenSetNullIsUsed_thenNewRecordIsCreated() throws Exception {
        Person person = new Person(1, "John", "Doe", null);

        try (PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL)) {
            preparedStatement.setInt(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getLastName());
            if (person.getAge() == null)
                preparedStatement.setNull(4, Types.INTEGER);
            else
                preparedStatement.setInt(4, person.getAge());
            int noOfRows = preparedStatement.executeUpdate();

            assertThat(noOfRows, equalTo(1));
        } catch (Exception ignored) {
        }
    }

    @Test
    public void givenNewPerson_whenSetObjectIsUsed_thenNewRecordIsCreated() throws Exception {
        Person person = new Person(2, "John", "Doe", null);

        try (PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL)) {
            preparedStatement.setInt(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getLastName());
            preparedStatement.setObject(4, person.getAge(), Types.INTEGER);
            int noOfRows = preparedStatement.executeUpdate();

            assertThat(noOfRows, equalTo(1));
        } catch (Exception ignored) {
        }
    }
}
