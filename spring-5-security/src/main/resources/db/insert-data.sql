insert into users values('john', 'marketing', '$2a$10$cjcbIX/aLe12PpGZ.vQfweLiB7K1QTC5enTk3oD0deCMdtj2Sx.im', true, true, true, true);
insert into users values('admin', 'sales', '$2a$10$cjcbIX/aLe12PpGZ.vQfweLiB7K1QTC5enTk3oD0deCMdtj2Sx.im', true, true, true, true);

insert into authorities values(1, 'john', 'USER');
insert into authorities values(2, 'admin', 'USER');
insert into authorities values(3, 'admin', 'ADMIN');