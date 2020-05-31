insert into users values('john', 'marketing', 'secret', true, true, true, true);
insert into users values('admin', 'sales', 'secret', true, true, true, true);

insert into authorities values(1, 'john', 'USER');
insert into authorities values(2, 'admin', 'USER');
insert into authorities values(3, 'admin', 'ADMIN');