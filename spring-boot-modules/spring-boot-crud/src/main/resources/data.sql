DROP TABLE IF EXISTS User;

CREATE TABLE User (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(30) NOT NULL,
  email VARCHAR(50) NOT NULL
);

INSERT INTO User (name, email) VALUES
  ('Pepe', 'pepe@gmail.com'),
  ('Mig', 'mig@baeldung.com');
