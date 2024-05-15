create table person (
	id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	first_name varchar(30),
	last_name varchar(30)
);