DROP TABLE IF EXISTS account;

CREATE TABLE account (
  accountNo Long NOT NULL,
  balance Numeric NOT NULL
);

Insert into account values (5,5000);