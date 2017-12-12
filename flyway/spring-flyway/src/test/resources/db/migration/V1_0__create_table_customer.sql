create table if not exists customer (
  id bigint AUTO_INCREMENT not null primary key,
  first_name varchar(255) ,
  last_name  varchar(255) ,
  email varchar(255)
);