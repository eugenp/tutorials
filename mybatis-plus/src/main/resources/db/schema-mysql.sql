CREATE DATABASE IF NOT EXISTS mybatisplus;

USE mybatisplus;

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