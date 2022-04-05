drop table if exists USERS;
drop table if exists country;
drop table if exists BOOK;

create table USERS(
  ID int not null AUTO_INCREMENT,
  NAME varchar(100) not null,
  STATUS int,
  PRIMARY KEY ( ID )
);

CREATE TABLE country (
  id   INTEGER      NOT NULL AUTO_INCREMENT,
  name VARCHAR(128) NOT NULL,
  PRIMARY KEY (id)
);

create table BOOK(
  ID int not null AUTO_INCREMENT,
  NAME varchar(128) not null,
  PRIMARY KEY ( ID )
);
