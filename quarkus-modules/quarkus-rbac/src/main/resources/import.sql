insert into roles (name, permissions) values ('ADMIN', 'VIEW_ADMIN_DETAILS,CREATE_USER,SEND_MESSAGE,VIEW_USER_DETAILS');
insert into roles (name, permissions) values ('USER', 'VIEW_USER_DETAILS,SEND_MESSAGE');
insert into roles (name, permissions) values ('GUEST', 'SEND_MESSAGE');

insert into users (id, username, password, email) values (1, 'admin', '$2a$10$sWfRL1ruggfeebSfeMnToOeeuQTzSFY.khIlS/dzWY6qYOekisccS', 'admin@test.io');
insert into users (id, username, password, email) values (2, 'user', '$2a$16$6AZvwlL1PCJ7fgoNVBlezOnoB6WkZlmj6mvQPP5/0uWyso8nVOdXm', 'user@test.io');
insert into users (id, username, password, email) values (3, 'guest', '$2a$16$Sd0wA6le90dUTe3OAUiSZe.FmPL96XLvRYUUbhitF3.dmgF/dLgFm', 'guest@test.io');

insert into user_roles (user_id, role_name) values (1, 'ADMIN');
insert into user_roles (user_id, role_name) values (2, 'USER');
insert into user_roles (user_id, role_name) values (3, 'GUEST');
