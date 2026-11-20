CREATE TABLE employee
(
    id         BIGINT  PRIMARY KEY,
    name       VARCHAR(100)   NOT NULL,
    title      VARCHAR(100)   NOT NULL,
    department VARCHAR(50)    NOT NULL,
    salary     NUMERIC(10, 2) NOT NULL,
    manager_id BIGINT REFERENCES employee (id)
);

INSERT INTO employee (id, name, title, department, salary, manager_id)
VALUES (1, 'Alice Turner', 'CEO', 'Executive', 220000, NULL),
       (2, 'Ben Rodgers', 'VP Engineering', 'Engineering', 180000, 1),
       (3, 'Carla Nunes', 'VP Sales', 'Sales', 175000, 1),
       (4, 'David Kim', 'Engineering Manager', 'Engineering', 140000, 2),
       (5, 'Ella Novak', 'Engineering Manager', 'Engineering', 138000, 2),
       (6, 'Frank Osei', 'Sales Manager', 'Sales', 120000, 3),
       (7, 'Grace Lin', 'Senior Engineer', 'Engineering', 115000, 4),
       (8, 'Hugo Alvarez', 'Software Engineer', 'Engineering', 95000, 4),
       (9, 'Ivy Chen', 'Software Engineer', 'Engineering', 98000, 5),
       (10, 'Jack Meyer', 'Sales Rep', 'Sales', 80000, 6),
       (11, 'Kara Diaz', 'Sales Rep', 'Sales', 82000, 6),
       (12, 'Liam Foster', 'Junior Engineer', 'Engineering', 75000, 7);