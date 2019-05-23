DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id bigint auto_increment primary key,
  title varchar(255),
  name varchar(255),
  country varchar(255)
);

insert into user values(1,'Mr','Norman Potter', 'USA');
insert into user values(2,'Miss','Ketty Smith', 'FRANCE');