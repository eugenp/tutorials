CREATE DATABASE IF NOT EXISTS mybatisplus;

USE mybatisplus;

DROP TABLE IF EXISTS account cascade;
DROP TABLE IF EXISTS client cascade;
DROP TABLE IF EXISTS ddl_history;

CREATE TABLE client
(
    id                 BIGINT AUTO_INCREMENT primary key,
    first_name         VARCHAR(255),
    last_name          VARCHAR(255),
    email              VARCHAR(255),
    creation_date      timestamp,
    last_modified_date timestamp
);

CREATE TABLE account
(
    id            BIGINT AUTO_INCREMENT primary key,
    principal     numeric(19, 10),
    interest_rate numeric(19, 10),
    term          int,
    client_id     BIGINT NOT NULL,
    constraint fk_account_client_id foreign key (client_id) references client (id)
);