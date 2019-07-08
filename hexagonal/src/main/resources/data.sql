DROP TABLE IF EXISTS FEED;

CREATE TABLE FEED (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  content VARCHAR(250) NOT NULL,
  published timestamp NOT NULL
);

INSERT INTO FEED (id, title, content, published) VALUES
  (1, 'Title1', 'Content1', '2019-01-01 00:00:00'),
  (2, 'Title2', 'Content2', '2019-01-02 00:00:00'),
  (3, 'Title3', 'Content3', '2019-01-03 00:00:00');
