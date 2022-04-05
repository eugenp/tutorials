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
