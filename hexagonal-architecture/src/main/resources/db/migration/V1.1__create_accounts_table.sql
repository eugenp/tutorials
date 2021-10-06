-- CREATE ACCOUNTS TABLE
DROP TABLE IF EXISTS accounts;

CREATE TABLE IF NOT EXISTS accounts
(
    id              INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    account_id      VARCHAR(250) NOT NULL,
    name      VARCHAR(250) NOT NULL,
    owner  	       VARCHAR(300) NOT NULL,
    balance          BIGINT NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL
);


