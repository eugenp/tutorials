CREATE OR REPLACE TABLE customers (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    ssn VARCHAR(50) NOT NULL,
    ccard VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO customers (name, ssn, ccard) 
VALUES
	('John Doe', '207-84-xxx4', '4804xxx169430405'),
	('Earl Sinclair', '363-37-xxx9', '4800xxx897829475'),
	('Jane Smith', '401-73-xxx6', '4800xxx138710414');

