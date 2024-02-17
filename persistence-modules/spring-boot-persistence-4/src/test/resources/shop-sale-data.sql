CREATE TABLE SHOP
(
    shop_id             int             AUTO_INCREMENT,
    shop_location       varchar(100)    NOT NULL UNIQUE,
    PRIMARY KEY(shop_id)
);


CREATE TABLE SHOP_TRANSACTION
(
    transaction_id      bigint          AUTO_INCREMENT,
    transaction_date    date            NOT NULL,
    shop_id             int             NOT NULL,
    amount              decimal(8,2)    NOT NULL,
    PRIMARY KEY(transaction_id),
    FOREIGN KEY(shop_id) REFERENCES SHOP(shop_id)
);


CREATE VIEW SHOP_SALE_VIEW AS
SELECT ROW_NUMBER() OVER () AS id, shop_id, shop_location, transaction_year, transaction_month, SUM(amount) AS total_amount
FROM (
    SELECT shop.shop_id, shop.shop_location, trans.amount, YEAR(transaction_date) AS transaction_year, MONTH(transaction_date) AS transaction_month
    FROM SHOP shop, SHOP_TRANSACTION trans
    WHERE shop.shop_id = trans.shop_id
) SHOP_MONTH_TRANSACTION
GROUP BY shop_id, transaction_year, transaction_month;


INSERT INTO SHOP(shop_location) VALUES ('Ealing');
INSERT INTO SHOP(shop_location) VALUES ('Richmond');

INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-01-05', 1, 3.49);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-01-31', 1, 7.29);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-02-09', 1, 1.60);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-02-17', 1, 5.99);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-02-18', 1, 5.99);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-03-01', 1, 8.99);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-03-22', 1, 5.49);

INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-01-15', 2, 8.99);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-01-18', 2, 8.99);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-02-01', 2, 5.99);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-02-05', 2, 2.50);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-03-01', 2, 5.99);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-03-02', 2, 6.60);
INSERT INTO SHOP_TRANSACTION(transaction_date, shop_id, amount) VALUES ('2024-03-17', 2, 1.19);
