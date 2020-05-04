DROP SCHEMA PUBLIC CASCADE

create table customer(
id integer identity primary key,
name varchar(50),
email varchar(50)

);
