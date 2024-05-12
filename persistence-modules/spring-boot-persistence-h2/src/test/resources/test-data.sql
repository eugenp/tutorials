--CREATE TABLE if not exists person_entity (id BIGINT PRIMARY KEY, mobile BIGINT, designation VARCHAR(255), name VARCHAR(255));
--CREATE SEQUENCE if not exists PERSON_SEQ START WITH 1 INCREMENT BY 1;
--insert into person_entity (ID, MOBILE, DESIGNATION,NAME) values (1, 88888888, 'CFO' , 'Tom');
--insert into person_entity (ID, MOBILE, DESIGNATION,NAME) values (2, 88888882, 'CEO' , 'Mark');
insert into person_entity (ID, MOBILE, DESIGNATION,NAME) values (NEXT VALUE FOR PERSON_SEQ, 88888888, 'CFO' , 'Tom');
insert into person_entity (ID, MOBILE, DESIGNATION,NAME) values (NEXT VALUE FOR PERSON_SEQ, 88888882, 'CEO' , 'Mark');