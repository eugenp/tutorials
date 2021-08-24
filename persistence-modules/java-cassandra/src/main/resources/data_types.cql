CREATE
    KEYSPACE baeldung
    WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};
USE baeldung;

CREATE TABLE numeric_types
(
    type1 int PRIMARY KEY,
    type2 bigint,
    type3 smallint,
    type4 tinyint,
    type5 varint,
    type6 float,
    type7 double,
    type8 decimal
);

CREATE TABLE text_types
(
    primaryKey int PRIMARY KEY,
    type2      text,
    type3      varchar,
    type4      ascii
);

CREATE TABLE date_types
(
    primaryKey int PRIMARY KEY,
    type1      timestamp,
    type2      time,
    type3      date,
    type4      timeuuid,
    type5      duration
);

CREATE TABLE other_types
(
    primaryKey int PRIMARY KEY,
    type1      boolean,
    type2      uuid,
    type3      blob,
    type4      inet
);

CREATE TABLE counter_type
(
    primaryKey uuid PRIMARY KEY,
    type1      counter
);

CREATE TABLE collection_types
(
    primaryKey int PRIMARY KEY,
    email      set<text>
);

ALTER TABLE collection_types
    ADD scores list<text>;

ALTER TABLE collection_types
    ADD address map<uuid, text>;

CREATE TABLE tuple_type
(
    primaryKey int PRIMARY KEY,
    type1      tuple<int, text, float>
);

CREATE TYPE user_defined_type ( type1 timestamp, type2 text, type3 text, type4 text);

CREATE TABLE user_type
(
    primaryKey int PRIMARY KEY,
    our_type   user_defined_type
);
