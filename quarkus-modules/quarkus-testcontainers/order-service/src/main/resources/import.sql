-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into customer (id)
values (1),
       (2);

insert into orders (id, description, customer_id)
values (1, 'Order #1', 1),
       (2, 'Order #2', 1),
       (3, 'Order #3', 1),
       (4, 'Order #4', 2);