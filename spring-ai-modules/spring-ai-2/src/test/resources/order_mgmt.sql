CREATE TABLE User_Order (
    order_id BIGINT NOT NULL PRIMARY KEY,
    user_id VARCHAR(20) NOT NULL,
    quantity INT
);

INSERT INTO User_Order (order_id, user_id, quantity) VALUES (1, 'Jenny', 2);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (2, 'Mary', 5);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (3, 'Alex', 1);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (4, 'John', 3);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (5, 'Sophia', 4);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (6, 'Jenny', 6);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (7, 'Mary', 2);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (8, 'Alex', 7);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (9, 'John', 1);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (10, 'Sophia', 8);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (11, 'Jenny', 3);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (12, 'Mary', 9);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (13, 'Alex', 2);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (14, 'John', 4);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (15, 'Sophia', 3);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (16, 'Jenny', 5);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (17, 'Mary', 6);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (18, 'Alex', 6);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (19, 'John', 2);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (20, 'Sophia', 1);
INSERT INTO User_Order (order_id, user_id, quantity) VALUES (21, 'David', 1);
