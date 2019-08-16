-- User user@email.com/pass
INSERT INTO bael_users (name, email, password, enabled) values ('user', 'user@email.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 1);

INSERT INTO authorities (email, authority) values ('user@email.com', 'ROLE_USER');