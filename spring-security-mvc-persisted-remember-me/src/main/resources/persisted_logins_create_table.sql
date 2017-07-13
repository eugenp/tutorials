-- SQL example for H2 (ran automatically by the spring config for the embedded H2 example)
create table if not exists persistent_logins ( 
 username varchar_ignorecase(100) not null, 
 series varchar(64) primary key, 
 token varchar(64) not null, 
 last_used timestamp not null 
);