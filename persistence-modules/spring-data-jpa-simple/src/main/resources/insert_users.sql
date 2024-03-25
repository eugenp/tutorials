insert into Users(name, creation_date, last_login_date, active, age, email, status)
values('John', PARSEDATETIME('01/01/2018', 'dd/MM/yyyy'), PARSEDATETIME('01/01/2020', 'dd/MM/yyyy'), 1, 23, 'john@email.com', 1);

insert into Users(name, creation_date, last_login_date, active, age, email, status)
values('Bob', PARSEDATETIME('02/02/2019', 'dd/MM/yyyy'), PARSEDATETIME('02/02/2020', 'dd/MM/yyyy'), 1, 56, 'bob@email.com', 1);

insert into Users(name, creation_date, last_login_date, active, age, email, status)
values('Cindy', PARSEDATETIME('02/02/2019', 'dd/MM/yyyy'), PARSEDATETIME('02/02/2020', 'dd/MM/yyyy'), 1, 18, 'cindy@email.com', 0);