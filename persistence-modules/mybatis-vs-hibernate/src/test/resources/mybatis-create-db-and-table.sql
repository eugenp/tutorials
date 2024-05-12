create or replace schema dbMyBatis;

CREATE SEQUENCE dbMyBatis.STUDENT_TBL_SEQ
START WITH 1
INCREMENT BY 1;

create table dbMyBatis.Student (
    id BIGINT default NEXT VALUE FOR dbMyBatis.STUDENT_TBL_SEQ PRIMARY KEY,
    name VARCHAR(255),
    phone INTEGER,
    email VARCHAR(255)
);