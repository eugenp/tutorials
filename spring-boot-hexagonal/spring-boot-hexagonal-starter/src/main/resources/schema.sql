drop table if exists ARTIST;

create table ARTIST
(
    ARTIST_ID bigint auto_increment primary key not null,
    NAME      VARCHAR(100)                      NOT NULL
);