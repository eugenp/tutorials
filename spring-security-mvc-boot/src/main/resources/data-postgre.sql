-- User user/pass
INSERT INTO users (username, password, enabled) values ('user', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', true);

INSERT INTO authorities (username, authority) values ('user', 'ROLE_USER');