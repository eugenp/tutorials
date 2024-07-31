CREATE TABLE users (
    user_id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO users(user_id, name) VALUES
    ('baeldung', 'Baeldung'),
    ('coxg', 'Graham');