drop table if exists CLOUDTECH;

create table CLOUDTECH
(
    CLOUDTECH_ID            bigint auto_increment primary key not null,
    CLOUD                   VARCHAR(100)                      NOT NULL,
    CLOUDTECH               VARCHAR(100)                      NOT NULL
);