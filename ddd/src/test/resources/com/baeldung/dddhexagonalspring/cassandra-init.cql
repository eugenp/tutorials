CREATE KEYSPACE IF NOT exists order_database
WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

CREATE TYPE IF NOT EXISTS order_database.orderitementity (productid uuid, price decimal); 

CREATE TABLE IF NOT EXISTS order_database.orderentity(
   id uuid,
   status text,
   orderitementities list<frozen<orderitementity>>,
   price decimal,
   primary key(id)
);

create type IF NOT EXISTS order_database.participant_entity (
	participantId uuid, 
	email text, 
	name text, 
	contact int,
	age int
);

CREATE TABLE IF NOT EXISTS order_database.event_entity (
    id uuid,
    event_location text,
    event_name text,
    event_status text,
    event_time timestamp,
    min_age int,
    organiser text,
    participants frozen<set<participant_entity>>,
    PRIMARY KEY(id)
);
	

