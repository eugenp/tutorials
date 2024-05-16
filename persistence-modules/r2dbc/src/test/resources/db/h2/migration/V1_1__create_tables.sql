CREATE TABLE department
(
    ID uuid DEFAULT random_uuid() PRIMARY KEY UNIQUE NOT NULL,
    NAME varchar(255)
);

CREATE TABLE student
(
    ID uuid DEFAULT random_uuid() UNIQUE NOT NULL,
    FIRST_NAME  varchar(255),
    LAST_NAME   varchar(255),
    DATE_OF_BIRTH  DATE NOT NULL,
    DEPARTMENT uuid NOT NULL
);


ALTER TABLE student
    ADD FOREIGN KEY (DEPARTMENT) REFERENCES department(ID);