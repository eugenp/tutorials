INSERT INTO department (id, name) VALUES (1, 'Infra');
INSERT INTO department (id, name) VALUES (2, 'Accounting');
INSERT INTO department (id, name) VALUES (3, 'Management');

INSERT INTO joins_employee (id, name, age, department_id) VALUES (1, 'Baeldung', '35', 1);
INSERT INTO joins_employee (id, name, age, department_id) VALUES (2, 'John', '35', 2);
INSERT INTO joins_employee (id, name, age, department_id) VALUES (3, 'Jane', '35', 2);

INSERT INTO phone (id, number, employee_id) VALUES (1, '111', 1);
INSERT INTO phone (id, number, employee_id) VALUES (2, '222', 1);
INSERT INTO phone (id, number, employee_id) VALUES (3, '333', 1);

COMMIT;
