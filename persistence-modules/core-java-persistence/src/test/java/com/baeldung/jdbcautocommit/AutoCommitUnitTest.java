package com.baeldung.jdbcautocommit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AutoCommitUnitTest {
    
    private static final String INSERT_PERSON_SQL = "INSERT INTO Person VALUES (?,?,?,?)";
    private static final String SELECT_ALL_PEOPLE_SQL = "SELECT * FROM Person";
    private static final String UPDATE_PERSON_AGE_BY_ID_SQL = "UPDATE Person SET age = ? WHERE id = ?";
    private static final String DELETE_ALL_PEOPLE_SQL = "DELETE FROM Person";
    private static final String UPDATE_PERSON_AGE_BY_NAME_SQL
      = "UPDATE Person SET age = ? WHERE id = (SELECT id FROM Person WHERE name = ?)";
    private static final String CREATE_PERSON_TABLE_SQL
      = "CREATE TABLE Person (id INTEGER not null, name VARCHAR(50), lastName VARCHAR(50), age INTEGER, PRIMARY KEY (id))";

    private static Connection connection1;
    private static Connection connection2;
    
    @BeforeAll
    static void setup() throws SQLException {
        connection1 = DriverManager.getConnection("jdbc:h2:mem:autocommit", "sa", "");
        createPersonTable(connection1);
        
        connection2 = DriverManager.getConnection("jdbc:h2:mem:autocommit", "sa", "");
    }
    
    @Test
    void givenAutoCommitTrue_whenInsertAndUpdateRecord_thenDataPersistedAfterEachWithoutCommit() throws SQLException {
        
        connection1.setAutoCommit(true);
        
        Person person = new Person(1, "John", "Doe", 45);        
        insertPerson(connection1, person);
        
        // no explicit commit needed here when auto-commit true

        // viewed from different connection, connection2 : assert person has been persisted into 
        // the database
        List<Person> people = selectAllPeople(connection2);
        assertThat("person record inserted OK into empty table", people.size(), is(equalTo(1)));        
        Person personInserted = people.iterator().next();
        assertThat("id correct", personInserted.getId(), is(equalTo(1)));

        // update age for person in database
        updatePersonAgeById(connection1, 1, 65);
        
        // no explicit commit needed here
        
        // viewed from connection2 : assert person's age has been updated to database
        people = selectAllPeople(connection2);
        Person personUpdated = people.iterator().next();
        assertThat("updated age correct", personUpdated.getAge(), is(equalTo(65)));
    }
    
    @Test
    void givenAutoCommitFalse_whenInsertCommitAndUpdateCommit_thenDataViewableAfterEachCommit() throws SQLException {
        
        connection1.setAutoCommit(false);
        
        Person person = new Person(1, "John", "Doe", 45);        
        insertPerson(connection1, person);

        // viewed from different connection, connection2 : assert that person has not yet been 
        // persisted to database before issuing commit
        List<Person> people = selectAllPeople(connection2);
        assertThat("No people have been inserted into database yet", people.size(), is(equalTo(0)));
        
        connection1.commit();
        
        // viewed from connection2 : assert that person has been persisted to database after 
        // issuing commit 
        people = selectAllPeople(connection2);
        assertThat("Person has been inserted into empty table after commit", people.size(), is(equalTo(1)));
        Person personInserted = people.iterator().next();
        assertThat("id correct", personInserted.getId(), is(equalTo(1)));
        
        updatePersonAgeById(connection1, 1, 65);
        
        // assert that person's age has not been updated before issuing commit
        people = selectAllPeople(connection2);
        Person personUpdated = people.iterator().next();
        assertThat("person's age still 45, not updated", personUpdated.getAge(), is(equalTo(45)));
        
        connection1.commit();

        // viewed from connection2 : assert that person's age has been updated after issuing commit
        people = selectAllPeople(connection2);
        personUpdated = people.iterator().next();
        assertThat("person's age updated to 65", personUpdated.getAge(), is(equalTo(65)));
    }

    @Test
    void givenAutoCommitFalse_whenInsertAndUpdateWithCommitOnlyAtEnd_thenDataOnlyViewableAfterCommit() throws SQLException {
        
        connection1.setAutoCommit(false);
        
        Person person = new Person(1, "John", "Doe", 45);        
        insertPerson(connection1, person);

        // viewed from different connection, connection2 : assert that person has not yet been 
        // persisted to database before issuing commit
        List<Person> people = selectAllPeople(connection2);
        assertThat("No people have been inserted into database yet", people.size(), is(equalTo(0)));
                
        updatePersonAgeById(connection1, 1, 65);
        
        // viewed from connection2 : assert that person has still not yet been 
        // persisted to database before issuing commit
        people = selectAllPeople(connection2);
        assertThat("No people have been inserted into database yet", people.size(), is(equalTo(0)));
        
        connection1.commit();

        // viewed from connection2 : assert that person record has been persisted to
        // database and person's age has been updated after issuing commit
        people = selectAllPeople(connection2);
        Person personUpdated = people.iterator().next();
        assertThat("person's age updated to 65", personUpdated.getAge(), is(equalTo(65)));
    }
    
    @Test
    void givenAutoCommitTrue_whenUpdateWithNestedSelect_thenUpdatePersistedWithoutCommit() throws SQLException {
        
        connection1.setAutoCommit(true);
        
        Person person = new Person(1, "John", "Doe", 45);        
        insertPerson(connection1, person);
        
        updatePersonAgeByName(connection1, "John", 77);
            
        // viewed from connection2 : assert person's age has been updated correctly to database
        // without issuing commit
        List<Person> people = selectAllPeople(connection2);
        Person personUpdated = people.iterator().next();
        assertThat("updated age correct", personUpdated.getAge(), is(equalTo(77)));        
    }
    
    @Test
    void givenAutoCommitFalse_whenModeChangedToTrueAfterSQLUpdate_thenUpdatePersistedWithoutCommit() throws SQLException {
        
        connection1.setAutoCommit(false);
        
        Person person = new Person(1, "John", "Doe", 45);        
        insertPerson(connection1, person);        
        updatePersonAgeByName(connection1, "John", 77);
        
        connection1.setAutoCommit(true);
            
        // viewed from connection2 : assert record inserted and person's age has been updated 
        // correctly to database after auto-commit changed false -> true ... without explicit commit needed
        List<Person> people = selectAllPeople(connection2);
        Person personUpdated = people.iterator().next();
        assertThat("updated age correct", personUpdated.getAge(), is(equalTo(77)));        
    }

    @AfterEach
    void deleteAllRecords() throws SQLException {
        
        if (connection1.getAutoCommit() == false) {
            connection1.setAutoCommit(true);
        }
        
        deleteAllPeople(connection1);
    }

    @AfterAll
    static void closeConnections() throws SQLException {
        
        connection1.close();
        connection2.close();
    }
    
    private static void createPersonTable(Connection connection) throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_PERSON_TABLE_SQL);
        }
    }
    
    private static int insertPerson(Connection connection, Person person) throws SQLException {
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PERSON_SQL)) {
            
            preparedStatement.setInt(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getLastName());
            preparedStatement.setInt(4, person.getAge());
            
            // execute statement and return the number of rows inserted
            return preparedStatement.executeUpdate();
        }        
    }
    
    private static void deleteAllPeople(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DELETE_ALL_PEOPLE_SQL);
        }
    }
    
    private static List<Person> selectAllPeople(Connection connection) throws SQLException {
        
        List<Person> people = null;
        
        try (Statement statement = connection.createStatement()) {

            people = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_PEOPLE_SQL);            

            while (resultSet.next()) {
                Person person = new Person();
                
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setLastName(resultSet.getString("lastName"));
                person.setAge(resultSet.getInt("age"));
                
                people.add(person);
            }
        }
        
        return people;
    }
    
    private static void updatePersonAgeById(Connection connection, int id, int newAge) throws SQLException {
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PERSON_AGE_BY_ID_SQL)) {
            
            preparedStatement.setInt(1, newAge);
            preparedStatement.setInt(2, id);
            
            preparedStatement.executeUpdate();
        }        
    }
    
    private static void updatePersonAgeByName(Connection connection, String name, int newAge) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PERSON_AGE_BY_NAME_SQL)) {
            
            preparedStatement.setInt(1, newAge);
            preparedStatement.setString(2, name);
            
            preparedStatement.executeUpdate();
        }
    }
}
