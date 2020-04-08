package com.baeldung.javahexagonal.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.baeldung.javahexagonal.domain.Attendee;
import com.baeldung.javahexagonal.domain.Event;

class JdbcEventRepositoryIntegrationTest {

    private DataSource dataSource;

    private JdbcEventRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1", "sa", "sa");

        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("schema.sql", JdbcEventRepositoryIntegrationTest.class));
        }

        repository = new JdbcEventRepository(dataSource);
    }

    @AfterEach
    void tearDown() {
        JdbcTestUtils.dropTables(new JdbcTemplate(dataSource), "event_attendees", "events");
    }

    @Test
    void getEvent() {
        Event event = repository.getEvent("junit");
        assertThat(event.getId()).isEqualTo("junit");
        assertThat(event.getName()).isEqualTo("Introduction to JUnit");
    }

    @Test
    void getEvent_forUnknownEvent_shouldThrowException() {
        assertThatThrownBy(() -> repository.getEvent("hamcrest")).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getAttendees() {
        Event event = repository.getEvent("junit");
        List<Attendee> attendees = repository.getAttendees(event);
        assertThat(attendees).hasSize(1);
        assertThat(attendees.get(0)
            .getName()).isEqualTo("John Doe");
    }

    @Test
    void addAttendee_withNewAttendee() {
        Event event = repository.getEvent("junit");
        Attendee attendee = new Attendee("Jane Doe", "info@example.org");
        repository.addAttendee(event, attendee);
        assertThat(repository.getAttendees(event)).hasSize(2);
    }

    @Test
    void addAttendee_withRegisteredAttendee_shouldThrowException() {
        Event event = repository.getEvent("junit");
        Attendee attendee = new Attendee("John Doe", "you@example.org");
        assertThatThrownBy(() -> repository.addAttendee(event, attendee)).isInstanceOf(DuplicateKeyException.class);
    }
}
