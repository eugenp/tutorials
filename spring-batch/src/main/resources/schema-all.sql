DROP TABLE medicine IF EXISTS;

CREATE TABLE medicine  (
    med_id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(30),
    type VARCHAR(30),
    expiration_date TIMESTAMP,
    original_price DECIMAL,
    sale_price DECIMAL
);