DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);

INSERT INTO customer (name) VALUES
  ('Baeldung'),
  ('Carlos'),
  ('Eugen'),
  ('Loredana'),
  ('Grzegorz'),
  ('Kevin'),
  ('Josh'),
  ('Michal'),
  ('Ashley'),
  ('Eric');