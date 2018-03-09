drop table if exists spittle;
drop table if exists spitter;

create table spitter (
  id identity,
  username varchar(25) not null,
  password varchar(25) not null,
  fullname varchar(100) not null,
  email varchar(50) not null,
  update_by_email boolean not null
);

create table spittle (
  id integer identity primary key,
  spitter_id integer not null,
  spittleText varchar(2000) not null,
  postedTime date not null,
  foreign key (spitter_id) references spitter(id)
);
