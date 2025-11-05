CREATE SEQUENCE IF NOT EXISTS person_seq INCREMENT 50;

CREATE TABLE IF NOT EXISTS person(
    id bigint NOT NULL,
    name character varying(255)
);
