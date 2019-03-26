--Monday 25/3/2019 

create table liquidity_utilized(
    id integer primary key,
    operationAmount integer,
    operationTS timestamp,
    totalAmount integer
);

create table liquidity_limit(
    id integer primary key,
    changeTS timestamp,
    amount integer
);

select max(id) from liquidity_utilized; --returns null initially;
