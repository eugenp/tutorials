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

INSERT INTO pet (pet_id, name, type, pet_detail_id)
VALUES (1, 'Ferdinand', 'BULL', 1);
INSERT INTO pet (pet_id, name, type, pet_detail_id)
VALUES (2, 'Jerry', 'JARBUL', 2);
INSERT INTO pet (pet_id, name, type, pet_detail_id)
VALUES (3, 'Puff', 'DRAGON', 3);

INSERT INTO user_pet (id, user_id, pet_id)
VALUES (1, 1, 1);
INSERT INTO user_pet (id, user_id, pet_id)
VALUES (2, 2, 2);
INSERT INTO user_pet (id, user_id, pet_id)
VALUES (3, 3, 3);

INSERT INTO pet_detail (pet_detail_id, description)
VALUES (1, 'PEACEFUL');
INSERT INTO pet_detail (pet_detail_id, description)
VALUES (2, 'SPACE OUTLAW');
INSERT INTO pet_detail (pet_detail_id, description)
VALUES (3, 'HIGH AS A KITE');