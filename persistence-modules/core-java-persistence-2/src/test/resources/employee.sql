/**
Script Name : Create employees script
Author: Parthiv Pradhan

**/

-- Create the employees table if it doesn't exist
CREATE TABLE  employees (
    id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    department VARCHAR(50),
    salary DECIMAL(10, 2)
);

-- Insert employee records
INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (1, 'John', 'Doe', 'HR', 50000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (2, 'Jane', 'Smith', 'IT', 60000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (3, 'Michael', 'Johnson', 'Finance', 55000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (4, 'Emily', 'Williams', 'Marketing', 52000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (5, 'David', 'Brown', 'IT', 65000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (6, 'Sarah', 'Miller', 'Finance', 58000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (7, 'Robert', 'Jones', 'HR', 53000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (8, 'Jessica', 'Davis', 'Marketing', 51000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (9, 'William', 'Wilson', 'IT', 59000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (10, 'Jennifer', 'Taylor', 'Finance', 57000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (11, 'Daniel', 'Anderson', 'Marketing', 54000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (12, 'Linda', 'Martinez', 'HR', 52000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (13, 'Christopher', 'Lopez', 'IT', 62000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (14, 'Karen', 'Hernandez', 'Finance', 56000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (15, 'Mark', 'Garcia', 'Marketing', 53000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (16, 'Patricia', 'Lee', 'HR', 51000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (17, 'Anthony', 'Clark', 'IT', 60000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (18, 'Maria', 'Lewis', 'Finance', 59000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (19, 'Paul', 'Walker', 'Marketing', 55000.00);

INSERT INTO employees (id, first_name, last_name, department, salary)
VALUES (20, 'Ruth', 'Young', 'HR', 54000.00);
