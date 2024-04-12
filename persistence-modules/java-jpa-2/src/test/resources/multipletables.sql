drop table if exists allergens;
drop table if exists meal;

create table meal (id bigint auto_increment, name varchar(255) not null, description varchar(255) not null, price decimal(19, 2) not null, primary key (id));
create table allergens (meal_id bigint auto_increment, peanuts number(1) not null, celery number(1) not null, sesame_seeds number(1) not null, primary key (meal_id));

insert into meal (id, name, description, price) values (1, 'Pizza', 'Delicious', 5);
insert into allergens (meal_id, peanuts, celery, sesame_seeds) values (1, 0, 1, 0);
