CREATE TABLE IF NOT EXISTS employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
INSERT INTO employee (name) VALUES ('John');
INSERT INTO employee (name) VALUES ('Jane');
UPDATE EMPLOYEE SET NAME = 'Jane Doe' WHERE ID = 2;
UPDATE employee SET NAME = 'John Doe' WHERE ID = 1;
SELECT * FROM employee;
