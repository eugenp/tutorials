drop table if exists client;

create table client (
  id numeric,
  name  varchar(50),
  constraint pk_client primary key (id)
);