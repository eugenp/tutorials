DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id bigint auto_increment primary key,
  title varchar(255),
  name varchar(255),
  country varchar(255)
);

insert into user values(1,'Mr.','Norman', 'USA');
insert into user values(2,'Mr.','Lewis', 'USA');