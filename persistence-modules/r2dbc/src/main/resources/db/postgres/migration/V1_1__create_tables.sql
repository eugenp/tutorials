
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS department
(
    id uuid PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    name varchar(255)
);

CREATE TABLE IF NOT EXISTS student
(
    id uuid PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    first_name  varchar(255),
    last_name   varchar(255),
    date_of_birth  DATE NOT NULL,
    department uuid NOT NULL CONSTRAINT student_foreign_key1 REFERENCES department (id)
);