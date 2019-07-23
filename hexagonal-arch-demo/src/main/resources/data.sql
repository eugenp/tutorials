DROP TABLE IF EXISTS quoteDBModel;

CREATE TABLE IF NOT EXISTS quoteDBModel (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  message VARCHAR(250) NOT NULL,
  author VARCHAR(250) NOT NULL
);

-- INSERT INTO quoteDBModel (id, message, author) VALUES
--   (100,'God is everywhere', 'Anonymous'),
--   (101,'God created man', 'Man'),
--   (102,'Man created God', 'God');