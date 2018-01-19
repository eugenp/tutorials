insert into User (id, firstName, lastName, email, age) values (1, 'john', 'doe', 'john@doe.com', 22);
insert into User (id, firstName, lastName, email, age) values (2, 'tom', 'doe', 'tom@doe.com', 26);

insert into MyUser (id, firstName, lastName, email, age) values (1, 'john', 'doe', 'john@doe.com', 22);
insert into MyUser (id, firstName, lastName, email, age) values (2, 'tom', 'doe', 'tom@doe.com', 26);

insert into Address (id, street, city, state, zipcode, checked, date_of_occupation, user_id) values (1, 'MAIN', 'SEATTLE', 'WA', '85123', true, CURRENT_DATE(), 1);
insert into Address (id, street, city, state, zipcode, checked, date_of_occupation, user_id) values (2, 'BOYLSTON', 'PHOENIX', 'AZ', '98102', false, DATEADD('YEAR',-7, CURRENT_DATE) , 2);
