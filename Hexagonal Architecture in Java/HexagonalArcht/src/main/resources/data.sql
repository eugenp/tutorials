DROP TABLE IF EXISTS student;
 
CREATE TABLE student (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  
);
 
INSERT INTO student (name) VALUES
  ('Mohan'),
  ('Ghanshyam'),
  ('Mayur');