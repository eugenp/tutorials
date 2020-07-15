DROP TABLE IF EXISTS movie;

CREATE TABLE movie(
  id int NOT NULL PRIMARY KEY,
  name VARCHAR(1024) NOT NULL,
  genre VARCHAR(255) not null,
  rating int not null
);

insert into movie(id, name, genre, rating)
values
(1, 'Lord of the rings', 'Fantasy', 5),
(2, 'Harry Potter', 'Fantasy', 4),
(3, 'Shawshank Redemption', 'Thriller', 4),
(4, 'Shutter Islands', 'Thriller', 5);
