drop table if exists users;

create table users (
  id bigint auto_increment, 
  username varchar(255), 
  password varchar(255), 
  enabled boolean);

drop table if exists authorities;

create table authorities (
  username varchar(255),
  authority varchar(255), 
  UNIQUE (username, authority));

