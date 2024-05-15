insert into users(username, password, enabled) values ('spring_user', 'pass', true);

insert into authorities(username, authority) values ('spring_user','ROLE_USER');