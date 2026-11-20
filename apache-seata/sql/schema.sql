CREATE TABLE shop_table (
    id TEXT PRIMARY KEY,
    created TIMESTAMP NOT NULL
);

CREATE TABLE inventory_table (
    id TEXT PRIMARY KEY,
    created TIMESTAMP NOT NULL
);

CREATE TABLE order_table (
    id TEXT PRIMARY KEY,
    created TIMESTAMP NOT NULL
);

CREATE TABLE billing_table (
    id TEXT PRIMARY KEY,
    created TIMESTAMP NOT NULL
);
