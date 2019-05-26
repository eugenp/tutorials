create table Product (id bigint not null, category varchar(255), description varchar(255), name varchar(255), unitPrice decimal(19,2), primary key (id));
insert into product(id, name, description, category) values (1,'Product Name 1','This is Product 1', 'category1');
insert into product(id, name, description, category) values (2,'Product Name 2','This is Product 2', 'category1');
insert into product(id, name, description, category) values (3,'Product Name 3','This is Product 3', 'category2');
insert into product(id, name, description, category) values (4,'Product Name 4','This is Product 4', 'category3');
