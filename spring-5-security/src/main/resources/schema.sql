drop table if exists users;
drop table if exists authorities;

create table users (
    username varchar(50) not null primary key,
    domain varchar(10) not null,
    password varchar(256) not null,
    enabled boolean not null,
    accountNonExpired boolean not null,
    credentialsNonExpired boolean not null,
    accountNonLocked boolean not null
);

create table authorities (
	id int not null primary key,
    username varchar(50) not null,
    authority varchar(50) not null
);
