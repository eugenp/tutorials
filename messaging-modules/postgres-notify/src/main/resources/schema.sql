create table if not exists orders (
	id serial primary key,
	symbol varchar(16) not null,
	order_type varchar(8) not null,
	price NUMERIC(10,2) not null,
	quantity NUMERIC(10,2) not null
);
