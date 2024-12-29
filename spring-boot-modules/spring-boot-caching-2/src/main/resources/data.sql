DROP TABLE IF EXISTS ITEM;
create table ITEM
(
    ID          CHARACTER VARYING not null,
    DESCRIPTION CHARACTER VARYING,
    constraint ITEM_PK
        primary key (ID)
);

INSERT INTO ITEM VALUES('abc','ITEM1');
