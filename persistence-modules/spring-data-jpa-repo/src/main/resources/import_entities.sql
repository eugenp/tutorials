insert into location (id, country, city) values (1, 'Country X', 'City One');
insert into location (id, country, city) values (2, 'Country X', 'City Two');
insert into location (id, country, city) values (3, 'Country X', 'City Three');

insert into store (id, name, location_id, items_sold, active) values (1, 'Store One', 3, 130000, true);
insert into store (id, name, location_id, items_sold, active) values (2, 'Store Two', 1, 170000, false);

insert into item_type (id, name) values (1, 'Food');
insert into item_type (id, name) values (2, 'Furniture');
insert into item_type (id, name) values (3, 'Electronics');

insert into item (id, name, store_id, item_type_id, price, grade, color) values (1, 'Food Item One', 1, 1, 100, 'A', 'Color x');
insert into item (id, name, store_id, item_type_id, price, grade, color) values (2, 'Furniture Item One', 1, 2, 2500, 'B', 'Color y');
insert into item (id, name, store_id, item_type_id, price, grade, color) values (3, 'Food Item Two', 1, 1, 35, 'A', 'Color z');
insert into item (id, name, store_id, item_type_id, price, grade, color) values (5, 'Furniture Item Two', 2, 2, 1600, 'A', 'Color w');
insert into item (id, name, store_id, item_type_id, price, grade, color) values (6, 'Food Item Three', 2, 1, 5, 'B', 'Color a');
insert into item (id, name, store_id, item_type_id, price, grade, color) values (7, 'Electronics Item One', 2, 3, 999, 'B', 'Color b');
