DROP TABLE IF EXISTS articles;

CREATE TABLE articles (
  id                   INT           AUTO_INCREMENT  PRIMARY KEY,
  name                 VARCHAR(250)  NOT NULL,
  last_modified_date   DATE          NOT NULL,
  visits               BIGINT        NOT NULL
);

INSERT INTO articles (name, last_modified_date, visits) VALUES
  ('Http Message Converters with the Spring Framework', '2020-02-13', 40000),
  ('HttpClient Basic Authentication ', '2020-02-12', 24000),
  ('Getting Started with Custom Deserialization in Jackson', '2019-12-24', 18000),
  ('Jackson – Custom Serializer', '2019-12-23', 16000),
  ('Getting Started with Custom Deserialization in Jackson', '2020-01-18', 9000);