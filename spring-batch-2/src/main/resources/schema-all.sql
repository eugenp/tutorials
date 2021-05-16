DROP TABLE coffee IF EXISTS;

CREATE TABLE coffee  (
    coffee_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    brand VARCHAR(20),
    origin VARCHAR(20),
    characteristics VARCHAR(30)
);