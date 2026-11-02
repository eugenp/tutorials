CREATE TABLE authors (
    author_id   BIGINT PRIMARY KEY,
    name        TEXT NOT NULL
);

CREATE TABLE books (
    book_id     BIGINT PRIMARY KEY,
    title       TEXT NOT NULL,
    author_id   BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors(author_id)
);

INSERT INTO authors (author_id, name) VALUES
    (1, 'George Orwell'),
    (2, 'Haruki Murakami'),
    (3, 'Agatha Christie'),
    (4, 'Ursula K. Le Guin');

INSERT INTO books (book_id, title, author_id) VALUES
    (101, '1984', 1),
    (102, 'Animal Farm', 1),
    (103, 'Norwegian Wood', 2),
    (104, 'Kafka on the Shore', 2),
    (105, 'Murder on the Orient Express', 3),
    (106, 'And Then There Were None', 3),
    (107, 'The Left Hand of Darkness', 4);