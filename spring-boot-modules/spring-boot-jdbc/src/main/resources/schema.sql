create table products (
                          id bigint primary key,
                          stock bigint
);

create table orders (
                        id bigint primary key,
                        product_id bigint
);

create table accounts (
                          id bigint primary key,
                          balance bigint
);

create table payments (
                          id bigint primary key,
                          amount bigint
);

insert into accounts(id, balance)
values (1, 1000);


insert into products(id, stock)
values (100, 10);
