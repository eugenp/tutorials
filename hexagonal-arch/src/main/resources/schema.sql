drop table if exists movie;

--table
create table movie(
	movie_id int,
	movie_name varchar(100),
	year_of_release varchar(4),
	movie_genre varchar(50),
	movie_rating decimal(10,2)
);

--Test data
insert into movie values(1, 'The Shawshank Redemption', '1994', 'drama',9.2);
insert into movie values(2, 'The dark knight', '2008', 'action',9.0);
insert into movie values(3, 'Schindlers List', '1993', 'biography',8.9);
insert into movie values(4, 'Pulp Fiction', '1994', 'thriller',8.9);
insert into movie values(5, 'Joker', '2019', 'crime',8.8);
insert into movie values(6, 'Avengers: Endgame', '2019', 'action',8.5);
insert into movie values(7, 'Avengers: Infinity War', '2018', 'action',8.5);
insert into movie values(8, 'Aquaman', '2018', 'action',7.0);
insert into movie values(9, 'War', '2019', 'thriller',6.8);
insert into movie values(10, 'Rampage', '2018', 'action',6.1);


