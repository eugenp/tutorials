CREATE TABLE flyway_test (
  key VARCHAR(64),
  value VARCHAR(255),
  PRIMARY KEY(key)
);

ALTER TABLE flyway_test OWNER TO flywaydemo;
