drop table if exists take_user;

create table take_user(
  id numeric,
  user  varchar(255),
  constraint pk_take_user primary key (id)
);