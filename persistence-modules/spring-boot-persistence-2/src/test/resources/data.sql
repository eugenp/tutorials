
insert into car_maker(id,name) values (99,'Special Motors');
insert into car_maker(id,name) values (100,'BWM');
insert into car_maker(id,name) values (102,'Dolores');

insert into car_model(id,maker_fk,name,sku,yearDate) values(132,99,'Muze','SM001',2018);
insert into car_model(id,maker_fk,name,sku,yearDate) values(145,99,'Empada','SM002',2008);

insert into car_model(id,maker_fk,name,sku,yearDate) values(43,100,'BWM-100','BWM100',2008);
insert into car_model(id,maker_fk,name,sku,yearDate) values(564,100,'BWM-200','BWM200',2009);
insert into car_model(id,maker_fk,name,sku,yearDate) values(343,100,'BWM-300','BWM300',2008);

