CREATE TABLE employee (
    id          IDENTITY NOT NULL PRIMARY KEY,
    name        VARCHAR(60) NOT NULL,
    age        	int NOT NULL,
    department  VARCHAR(40) NOT NULL
);