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

select * from liquidity_limit ORDER BY id asc;
select * from liquidity_utilized order by id asc;

select id, amount from liquidity_limit where id = (select max(id) from liquidity_limit); 

select id, totalAmount from liquidity_utilized where id = (select max(id) from liquidity_utilized);
