CREATE TABLE IF NOT EXISTS phone_order (
    `order` varchar(255) PRIMARY KEY,
    `where` varchar(255)
);

CREATE TABLE IF NOT EXISTS broken_phone_order (
    `order` varchar(255) PRIMARY KEY,
    `where` varchar(255)
);