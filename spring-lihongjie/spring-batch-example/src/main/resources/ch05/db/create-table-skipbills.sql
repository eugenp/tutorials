drop table if exists skipbills;

create table skipbills
(
  line character(9) not null,
  content character varying(512),
  constraint skipbills_pkey primary key (line)
);

