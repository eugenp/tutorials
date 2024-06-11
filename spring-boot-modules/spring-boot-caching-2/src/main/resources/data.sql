DROP TABLE IF EXISTS ITEM;
create table ITEM
(
    ID          CHARACTER VARYING not null,
    DESCRIPTION CHARACTER VARYING,
    constraint ITEM_PK
        primary key (ID)
);
DROP TABLE IF EXISTS HOTEL;
DROP TABLE IF EXISTS CITY;
create table CITY
(
    id                    bigint,
    name                  varchar,
    city_centre_lattitude double,
    city_centre_longitude double,
    constraint city_pk
        primary key (id)
);
create table hotel
(
    id        bigint auto_increment,
    name      varchar,
    deleted   boolean,
    rating    double,
    city_id   bigint,
    address   varchar,
    lattitude varchar,
    longitude varchar,
    constraint hotel_pk
        primary key (id),
    constraint "hotel_CITY_null_fk"
        foreign key (city_id) references CITY (id)
);


INSERT INTO ITEM VALUES('abc','ITEM1');


INSERT INTO city(id, name, city_centre_lattitude, city_centre_longitude)
VALUES (1, 'Amsterdam', 52.368780, 4.903303);
INSERT INTO city(id, name, city_centre_lattitude, city_centre_longitude)
VALUES (2, 'Manchester', 53.481062, -2.237706);


INSERT INTO hotel(name, deleted, rating, city_id, address, lattitude, longitude)
VALUES ('Monaghan Hotel', false, 9.2, 1, 'Weesperbuurt en Plantage', 52.364799, 4.908971);
INSERT INTO hotel(name, deleted, rating, city_id, address, lattitude, longitude)
VALUES ('The Thornton Council Hotel', false, 6.3, 1, 'Waterlooplein', 52.3681563, 4.9010029);
INSERT INTO hotel(name, deleted, rating, city_id, address, lattitude, longitude)
VALUES ('McZoe Trescothiks Hotel', false, 9.8, 1, 'Oude Stad, Harlem', 52.379577, 4.633547);
INSERT INTO hotel(name, deleted, rating, city_id, address, lattitude, longitude)
VALUES ('Stay Schmtay Hotel', false, 8.7, 1, 'Jan van Galenstraat', 52.3756755, 4.8668628);
INSERT INTO hotel(name, deleted, rating, city_id, address, lattitude, longitude)
VALUES ('Fitting Image Hotel', false, NULL, 1, 'Staatsliedenbuurt', 52.380936, 4.8708297);
INSERT INTO hotel(name, deleted, rating, city_id, address, lattitude, longitude)
VALUES ('Raymond of Amsterdam Hotel', false, NULL, 1, '22 High Avenue', 52.3773989, 4.8846443);
INSERT INTO hotel(name, deleted, rating, city_id, address, lattitude, longitude)
VALUES ('201 Deansgate Hotel', false, 7.3, 2, '201 Deansgate', 53.4788305, -2.2484721);
INSERT INTO hotel(name, deleted, rating, city_id, address, lattitude, longitude)
VALUES ('Fountain Street Hotel', true, 3.0, 2, '35 Fountain Street', 53.4811298, -2.2402227);
INSERT INTO hotel(name, deleted, rating, city_id, address, lattitude, longitude)
VALUES ('Sunlight House', false, 4.3, 2, 'Little Quay St', 53.4785129, -2.2505943);
INSERT INTO hotel(name, deleted, rating, city_id, address, lattitude, longitude)
VALUES ('St Georges House', false, 9.6, 2, '56 Peter St', 53.477822, -2.2462002);
INSERT INTO hotel(name, deleted, rating, city_id, address, lattitude, longitude)
VALUES ('Marriot Bonvoy', false, 9.6, 1, 'Hans Zimmerstraat', 53.477872, -2.2462003);