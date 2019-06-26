DROP TABLE IF EXISTS messages;
 
CREATE TABLE messages (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  message VARCHAR(250) NOT NULL,
  message_type VARCHAR(10) NOT NULL
);
 
INSERT INTO messages (id, message, message_type) VALUES
  (1, 'Hey! Welcome', 'String'),
  (2, 'Java Ecosystem', 'String'),
  (3, 'Hexagonal Architecture', 'String');