package com.baeldung.statmentVsPreparedstatment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StatementPersonDaoUnitTest {

    private StatementPersonDao dao;

    @BeforeEach
    void setup() throws SQLException, ClassNotFoundException {
        DatasourceFactory datasourceFactory = new DatasourceFactory();
        Connection connection = datasourceFactory.getConnection();
        datasourceFactory.createTables();
        dao = new StatementPersonDao(connection);
    }

    @Test
    void whenInsertAPerson_thenItNeverThrowsAnException() {
        assertDoesNotThrow(() -> dao.insert(new PersonEntity(1, "john")));
    }

    @Test
    void whenInsertAPersonWithQuoteInText_thenItWillThrowAnException() {
        assertThrows(SQLException.class, () -> dao.insert(new PersonEntity(1, "O'Brien")));
    }

    @Test
    void whenGetAPersonById_thenItReturnThePersonInDatabase() throws SQLException {
        dao.insert(new PersonEntity(1, "john"));

        Optional<PersonEntity> maybeEmployee = dao.getById(1);

        assertTrue(maybeEmployee.isPresent());

        PersonEntity personEntity = maybeEmployee.get();

        assertEquals(1, personEntity.getId());
        assertEquals("john", personEntity.getName());
    }

    @Test
    void whenInsertAndGetMultiplePersons_thenItNeverThrowsAnException() throws SQLException {
        assertDoesNotThrow(() -> dao.insert(
            Arrays.asList(new PersonEntity(1, "john"), new PersonEntity(2, "skeet"))));

        List<PersonEntity> result = dao.getAll();

        assertEquals(Arrays.asList(new PersonEntity(1, "john"), new PersonEntity(2, "skeet")),
            result);
    }

    @Test
    void whenUpdateAnExistentPerson_thenItReturnsTheUpdatedPerson() throws SQLException {
        dao.insert(new PersonEntity(1, "john"));
        dao.update(new PersonEntity(1, "johnny"));

        Optional<PersonEntity> maybePerson = dao.getById(1);

        assertTrue(maybePerson.isPresent());

        PersonEntity personEntity = maybePerson.get();

        assertEquals(1, personEntity.getId());
        assertEquals("johnny", personEntity.getName());
    }

    @Test
    void whenDeleteAPersonById_thenItWillBeAbsentInDatabase() throws SQLException {
        dao.insert(new PersonEntity(1, "john"));
        dao.deleteById(1);

        Optional<PersonEntity> maybePerson = dao.getById(1);

        assertFalse(maybePerson.isPresent());
    }

    @Test
    void whenAHackerUpdateAPerson_thenItAllPersonsAreUpdated() throws SQLException {
        dao.insert(Arrays.asList(new PersonEntity(1, "john"), new PersonEntity(2, "skeet")));
        dao.update(new PersonEntity(1, "hacker' --"));

        List<PersonEntity> result = dao.getAll();

        assertEquals(Arrays.asList(new PersonEntity(1, "hacker"), new PersonEntity(2, "hacker")),
            result);
    }
}