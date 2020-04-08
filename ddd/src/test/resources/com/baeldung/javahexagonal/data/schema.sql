CREATE TABLE events (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE event_attendees (
    event_id VARCHAR(10),
    name VARCHAR(50),
    mail VARCHAR(50),
    FOREIGN KEY (event_id) REFERENCES events (id),
    UNIQUE KEY event_attendee_name (event_id, name)
);

INSERT INTO events (id, name) VALUES ('junit', 'Introduction to JUnit');
INSERT INTO event_attendees (event_id, name, mail) VALUES ('junit', 'John Doe', 'you@example.org');
