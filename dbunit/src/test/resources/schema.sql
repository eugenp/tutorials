CREATE TABLE IF NOT EXISTS USER
(
  id         int AUTO_INCREMENT NOT NULL,
  email varchar(100)       UNIQUE NOT NULL,
  registered_at TIMESTAMP,
  PRIMARY KEY (id)
);

