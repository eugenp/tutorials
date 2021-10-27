create database hexagonal_architecture;
create user testuser with encrypted password 'testuser';
grant all privileges on database hexagonal_architecture to testuser;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO testuser;

CREATE TABLE car (
    id serial PRIMARY KEY,
    mark VARCHAR ( 50 ) UNIQUE NOT NULL,
    category VARCHAR ( 50 ) UNIQUE NOT NULL,
    price decimal(17,8),
    construction_year INT NOT NULL
    );

INSERT INTO car (mark, category, price, construction_year)
VALUES ('BMW', 'COMPACT', 10000, 2012);