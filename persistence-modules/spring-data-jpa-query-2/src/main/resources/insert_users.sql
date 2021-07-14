insert into Users(id, name, creation_date, last_login_date, active, age, email, status)
values(1, 'John', TO_DATE('01/01/2018', 'DD/MM/YYYY'), TO_DATE('01/01/2020', 'DD/MM/YYYY'), 1, 23, 'john@email.com', 1);

insert into Users(id, name, creation_date, last_login_date, active, age, email, status)
values(2, 'Bob', TO_DATE('02/02/2019', 'DD/MM/YYYY'), TO_DATE('02/02/2020', 'DD/MM/YYYY'), 1, 56, 'bob@email.com', 1);

insert into Users(id, name, creation_date, last_login_date, active, age, email, status)
values(3, 'Cindy', TO_DATE('02/02/2019', 'DD/MM/YYYY'), TO_DATE('02/02/2020', 'DD/MM/YYYY'), 1, 18, 'cindy@email.com', 0);