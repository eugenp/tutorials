DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
  id bigint auto_increment primary key,
  title varchar(255),
  name varchar(255)
)