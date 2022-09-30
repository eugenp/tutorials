insert into LOCATION values('07092', 'Mountainside', 'NJ');
insert into LOCATION values ('94118', 'San Francisco', 'CA');
insert into LOCATION values ('10002', 'New York', 'NY');

insert into VEHICLE (vin, year, make, model, trim, fk_location) values('KM8JN72DX7U587496', 2007, 'Hyundai', 'Tucson', null, '07092');
insert into VEHICLE (vin, year, make, model, trim, fk_location) values('JTKKU4B41C1023346', 2012, 'Toyota', 'Scion', 'Xd', '94118');
insert into VEHICLE (vin, year, make, model, trim, fk_location) values('1G1JC1444PZ215071', 2000, 'Chevrolet', 'CAVALIER VL', 'RS', '07092');