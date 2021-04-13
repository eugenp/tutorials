DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  login VARCHAR(250) NOT NULL,
  name VARCHAR(250) NOT NULL,
  surname VARCHAR(250) DEFAULT NULL
);

INSERT INTO USER (login, name, surname) VALUES
  ('mjordan', 'Michael', 'Jordan'),
  ('ljames', 'Lebron', 'James'),
  ('mjohnson', 'Magic', 'Johnson')
