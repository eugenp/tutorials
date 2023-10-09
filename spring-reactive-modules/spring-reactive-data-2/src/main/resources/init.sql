create table product
(
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    name  varchar(50),
    price decimal
);

insert into product(name, price)
values ('product_A', 1.0);
insert into product(name, price)
values ('product_B', 2.0);
insert into product(name, price)
values ('product_C', 3.0);
insert into product(name, price)
values ('product_D', 4.0);
