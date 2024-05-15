DROP TABLE IF EXISTS person;
CREATE TABLE person(
	id INT PRIMARY KEY,
	first_name VARCHAR(200),
	last_name VARCHAR(200)
);

DROP TABLE IF EXISTS post;
CREATE TABLE post(
	id INT PRIMARY KEY,
	title VARCHAR(200),
	publication_date DATE
)