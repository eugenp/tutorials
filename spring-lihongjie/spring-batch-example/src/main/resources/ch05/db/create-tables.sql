drop table if exists counter;

create table counter
(
  id character(9) not null,
  name character varying(50),
  description character varying(128),
  constraint counter_pkey primary key (id)
);

