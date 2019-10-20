CREATE TABLE users (
  username VARCHAR(256) PRIMARY KEY,
  password VARCHAR(256),
  enabled  BOOLEAN
);

CREATE TABLE authorities (
  username  VARCHAR(256) REFERENCES users (username),
  authority VARCHAR(256)
);
