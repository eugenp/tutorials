insert into Address (id, street, city, state, zipcode) values (1, 'MAIN', 'SEATTLE', 'WA', '85123');
insert into Address (id, street, city, state, zipcode) values (2, 'BOYLSTON', 'PHOENIX', 'AZ', '98102');

insert into User (id, firstName, lastName, email, age, address_id) values (1, 'john', 'doe', 'john@doe.com', 22, 1);
insert into User (id, firstName, lastName, email, age, address_id) values (2, 'tom', 'doe', 'tom@doe.com', 26, 2);

insert into MyUser (id, firstName, lastName, email, age) values (1, 'john', 'doe', 'john@doe.com', 22);
insert into MyUser (id, firstName, lastName, email, age) values (2, 'tom', 'doe', 'tom@doe.com', 26);
