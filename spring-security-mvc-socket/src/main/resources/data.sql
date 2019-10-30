INSERT INTO user (user_id, username, password)
VALUES (1, 'user', 'password');
INSERT INTO user (user_id, username, password)
VALUES (2, 'admin','password');
INSERT INTO user (user_id, username, password)
VALUES (3, 'mary','password');

INSERT INTO role (role_id, role)
VALUES (1, 'USER');
INSERT INTO role (role_id, role)
VALUES (2, 'ADMIN');

INSERT INTO user_role (id, user_id, role_id)
VALUES (1, 1, 1);
INSERT INTO user_role (id, user_id, role_id)
VALUES (2, 2, 2);
INSERT INTO user_role (id, user_id, role_id)
VALUES (3, 3, 1);