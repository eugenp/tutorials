CREATE TABLE employee(
    id int NOT NULL PRIMARY KEY auto_increment,
    firstname varchar(255),
    lastname varchar(255),
    salary double,
    hireddate date
);

CREATE TABLE email(
    id int NOT NULL PRIMARY KEY auto_increment,
    employeeid int,
    address varchar(255)
);

CREATE TABLE employee_legacy(
    id int NOT NULL PRIMARY KEY auto_increment,
    first_name varchar(255),
    last_name varchar(255),
    salary double,
    hired_date date
);


INSERT INTO employee (firstname,lastname,salary,hireddate) VALUES ('John', 'Doe', 10000.10, PARSEDATETIME('20010101','yyyyMMdd'));
INSERT INTO employee (firstname,lastname,salary,hireddate) VALUES ('Kevin', 'Smith', 20000.20, PARSEDATETIME('20020202','yyyyMMdd'));
INSERT INTO employee (firstname,lastname,salary,hireddate) VALUES ('Kim', 'Smith', 30000.30, PARSEDATETIME('20030303','yyyyMMdd'));
INSERT INTO employee (firstname,lastname,salary,hireddate) VALUES ('Stephen', 'Torvalds', 40000.40, PARSEDATETIME('20040404','yyyyMMdd'));
INSERT INTO employee (firstname,lastname,salary,hireddate) VALUES ('Christian', 'Reynolds', 50000.50, PARSEDATETIME('20050505','yyyyMMdd'));

INSERT INTO employee_legacy (first_name,last_name,salary,hired_date) VALUES ('John', 'Doe', 10000.10, PARSEDATETIME('20010101','yyyyMMdd'));
INSERT INTO employee_legacy (first_name,last_name,salary,hired_date) VALUES ('Kevin', 'Smith', 20000.20, PARSEDATETIME('20020202','yyyyMMdd'));
INSERT INTO employee_legacy (first_name,last_name,salary,hired_date) VALUES ('Kim', 'Smith', 30000.30, PARSEDATETIME('20030303','yyyyMMdd'));
INSERT INTO employee_legacy (first_name,last_name,salary,hired_date) VALUES ('Stephen', 'Torvalds', 40000.40, PARSEDATETIME('20040404','yyyyMMdd'));
INSERT INTO employee_legacy (first_name,last_name,salary,hired_date) VALUES ('Christian', 'Reynolds', 50000.50, PARSEDATETIME('20050505','yyyyMMdd'));

INSERT INTO email (employeeid,address) VALUES (1, 'john@baeldung.com');
INSERT INTO email (employeeid,address) VALUES (1, 'john@gmail.com');
INSERT INTO email (employeeid,address) VALUES (2, 'kevin@baeldung.com');
INSERT INTO email (employeeid,address) VALUES (3, 'kim@baeldung.com');
INSERT INTO email (employeeid,address) VALUES (3, 'kim@gmail.com');
INSERT INTO email (employeeid,address) VALUES (3, 'kim@outlook.com');
INSERT INTO email (employeeid,address) VALUES (4, 'stephen@baeldung.com');
INSERT INTO email (employeeid,address) VALUES (5, 'christian@gmail.com');
