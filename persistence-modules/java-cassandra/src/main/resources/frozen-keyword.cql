CREATE KEYSPACE mykeyspace WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

USE mykeyspace;

CREATE TABLE mykeyspace.users
(
    id         uuid PRIMARY KEY,
    ip_numbers frozen<set<inet>>,
    addresses  frozen<map<text, tuple<text>>>,
    emails     frozen<list<varchar>>,
);

INSERT INTO mykeyspace.users (id, ip_numbers)
VALUES (6ab09bec-e68e-48d9-a5f8-97e6fb4c9b47,
        { '10.10.11.1', '10.10.10.1', '10.10.12.1'});

UPDATE mykeyspace.users
SET ip_numbers = ip_numbers + {'10.10.14.1'}
WHERE id = 6ab09bec-e68e-48d9-a5f8-97e6fb4c9b47;

UPDATE mykeyspace.users
SET ip_numbers = {'11.10.11.1', '11.10.10.1', '11.10.12.1'}
WHERE id = 6ab09bec-e68e-48d9-a5f8-97e6fb4c9b47;

SELECT ip_numbers
FROM mykeyspace.users;

CREATE TABLE mykeyspace.users_score
(
    id    uuid PRIMARY KEY,
    score set<frozen<set<int>>>
);

CREATE TYPE mykeyspace.address (
    city text,
    street text,
    streetNo int,
    zipcode text
    );

CREATE TABLE mykeyspace.building
(
    id      uuid PRIMARY KEY,
    address frozen<address>
);

INSERT INTO mykeyspace.building (id, address)
VALUES (6ab09bec-e68e-48d9-a5f8-97e6fb4c9b48,
        {city: 'City', street: 'Street', streetNo: 2,zipcode: '02-212'});

UPDATE mykeyspace.building
SET address.city = 'City2'
WHERE id = 6ab09bec-e68e-48d9-a5f8-97e6fb4c9b48;

UPDATE mykeyspace.building
SET address = {city : 'City2', street : 'Street2'}
WHERE id = 6ab09bec-e68e-48d9-a5f8-97e6fb4c9b48;