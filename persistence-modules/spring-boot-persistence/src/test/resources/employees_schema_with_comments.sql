--Drop the table
drop table EMPLOYEES if exists;

--Create the table
create table EMPLOYEES(
  ID int not null AUTO_INCREMENT,
  NAME varchar(100) not null,
  TITLE varchar(100),
  PRIMARY KEY ( ID )
);
