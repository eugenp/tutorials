package com.baeldung.javahexagonal.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.baeldung.javahexagonal.domain.Attendee;
import com.baeldung.javahexagonal.domain.Event;
import com.baeldung.javahexagonal.domain.EventRepository;

@Repository
public class JdbcEventRepository implements EventRepository {

    private final JdbcTemplate jdbc;

    public JdbcEventRepository(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public Event getEvent(String id) {
        return jdbc.queryForObject("SELECT id, name FROM events WHERE id = ?", new EventRowMapper(), id);
    }

    @Override
    public List<Attendee> getAttendees(Event event) {
        return jdbc.query("SELECT name, mail FROM event_attendees WHERE event_id = ?", new AttendeeRowMapper(), event.getId());
    }

    @Override
    public void addAttendee(Event event, Attendee attendee) {
        jdbc.update("INSERT INTO event_attendees (event_id, name, mail) VALUES (?, ?, ?)", event.getId(), attendee.getName(), attendee.getMailAddress());
    }

    private static class EventRowMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            return new Event(id, name);
        }
    }

    private static class AttendeeRowMapper implements RowMapper<Attendee> {
        @Override
        public Attendee mapRow(ResultSet resultSet, int i) throws SQLException {
            String name = resultSet.getString("name");
            String mailAddress = resultSet.getString("mail");
            return new Attendee(name, mailAddress);
        }
    }
}
