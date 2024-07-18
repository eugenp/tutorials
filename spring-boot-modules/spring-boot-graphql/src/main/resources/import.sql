create table LOCATION("zipcode" varchar(5) not null, "city" varchar(100) not null, "state" varchar(2) not null);

create table VEHICLE("vin" varchar(100) not null, "year" integer not null, "make" varchar(100) not null, "model" varchar(100) not null, "trim" varchar(100), "fk_LOCATION" varchar(5) not null);

insert into "LOCATION" ("zipcode", "city", "state") values ('07092', 'Mountainside', 'NJ');
insert into "LOCATION" ("zipcode", "city", "state") values ('94118', 'San Francisco', 'CA');
insert into "LOCATION" ("zipcode", "city", "state") values ('10002', 'New York', 'NY');

insert into "VEHICLE" ("vin", "year", "make", "model", "trim", "fk_LOCATION") values('KM8JN72DX7U587496', 2007, 'Hyundai', 'Tucson', null, '07092');
insert into "VEHICLE" ("vin", "year", "make", "model", "trim", "fk_LOCATION") values('JTKKU4B41C1023346', 2012, 'Toyota', 'Scion', 'Xd', '94118');
insert into "VEHICLE" ("vin", "year", "make", "model", "trim", "fk_LOCATION") values('1G1JC1444PZ215071', 2000, 'Chevrolet', 'CAVALIER VL', 'RS', '07092');
