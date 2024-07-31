CREATE KEYSPACE inventory
WITH replication = {
	'class' : 'NetworkTopologyStrategy',
	'datacenter1' : 3
};

use inventory;

CREATE TABLE vehicles (
   vin text PRIMARY KEY,
   year int,
   make varchar,
   model varchar
);

consistency LOCAL_QUORUM;

insert into vehicles (vin, year, make, model) values ('387KSJHFK23874GH', 2020, 'Ford', 'F-150');
insert into vehicles (vin, year, make, model) values ('534HNDHFK23873EF', 2020, 'Honda', 'Accord');
insert into vehicles (vin, year, make, model) values ('953TOYJEK23853DB', 2020, 'Toyota', 'Camry');