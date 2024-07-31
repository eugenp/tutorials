create table users (
    username varchar(50) not null primary key,
    password varchar(256) not null,
    enabled boolean not null
);


create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null
);


create table preferences (
    username varchar(50) not null,
    theme varchar(50)
);