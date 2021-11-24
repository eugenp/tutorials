DROP TABLE IF EXISTS author_book, author, book;

CREATE TABLE author (
  id             INT          NOT NULL PRIMARY KEY,
  first_name     VARCHAR(50),
  last_name      VARCHAR(50)  NOT NULL
);

CREATE TABLE book (
  id             INT          NOT NULL PRIMARY KEY,
  title          VARCHAR(100) NOT NULL
);

CREATE TABLE author_book (
  author_id      INT          NOT NULL,
  book_id        INT          NOT NULL,
  
  PRIMARY KEY (author_id, book_id),
  CONSTRAINT fk_ab_author     FOREIGN KEY (author_id)  REFERENCES author (id)  
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_ab_book       FOREIGN KEY (book_id)    REFERENCES book   (id)
);

INSERT INTO author VALUES 
  (1, 'Kathy', 'Sierra'), 
  (2, 'Bert', 'Bates'), 
  (3, 'Bryan', 'Basham');

INSERT INTO book VALUES 
  (1, 'Head First Java'), 
  (2, 'Head First Servlets and JSP'),
  (3, 'OCA/OCP Java SE 7 Programmer');

INSERT INTO author_book VALUES (1, 1), (1, 3), (2, 1);