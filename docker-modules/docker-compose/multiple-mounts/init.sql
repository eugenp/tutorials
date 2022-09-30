CREATE DATABASE IF NOT EXISTS test;

use test;

CREATE TABLE IF NOT EXISTS test_table (id int, description varchar(255));

INSERT INTO test_table VALUES (1, 'TEST_1');
INSERT INTO test_table VALUES (2, 'TEST_2');
INSERT INTO test_table VALUES (3, 'TEST_3');