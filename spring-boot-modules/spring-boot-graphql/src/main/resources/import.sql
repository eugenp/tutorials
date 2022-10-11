insert into "location" values('07092', 'Mountainside', 'NJ');
insert into "location" values ('94118', 'San Francisco', 'CA');
insert into "location" values ('10002', 'New York', 'NY');

insert into "vehicle" ("vin", "year", "make", "model", "trim", "fk_location") values('KM8JN72DX7U587496', 2007, 'Hyundai', 'Tucson', null, '07092');
insert into "vehicle" ("vin", "year", "make", "model", "trim", "fk_location") values('JTKKU4B41C1023346', 2012, 'Toyota', 'Scion', 'Xd', '94118');
insert into "vehicle" ("vin", "year", "make", "model", "trim", "fk_location") values('1G1JC1444PZ215071', 2000, 'Chevrolet', 'CAVALIER VL', 'RS', '07092');