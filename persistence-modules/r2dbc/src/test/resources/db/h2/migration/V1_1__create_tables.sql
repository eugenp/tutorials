
CREATE TABLE department
(
    id uuid DEFAULT random_uuid() PRIMARY KEY UNIQUE NOT NULL,
    name varchar(255)
);

CREATE TABLE student
(
    id uuid DEFAULT random_uuid() UNIQUE NOT NULL,
    first_name  varchar(255),
    last_name   varchar(255),
    date_of_birth  DATE NOT NULL,
    department uuid NOT NULL
);


ALTER TABLE student
    ADD FOREIGN KEY (department) REFERENCES department(id);