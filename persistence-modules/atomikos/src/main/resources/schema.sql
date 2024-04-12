CREATE TABLE INVENTORY (
    productId VARCHAR PRIMARY KEY,
    balance INT
);

CREATE TABLE ORDERS (
    orderId VARCHAR PRIMARY KEY,
    productId VARCHAR,
    amount INT NOT NULL CHECK (amount <= 5)
);