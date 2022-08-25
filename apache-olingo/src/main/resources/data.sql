insert into "car_maker"("id", "name") values (1,'Special Motors');
insert into "car_maker"("id", "name") values (2,'BWM');
insert into "car_maker"("id", "name") values (3,'Dolores');

insert into "car_model"("id", "maker_fk", "name", "sku", "year") values(1,1,'Muze','SM001',2018);
insert into "car_model"("id", "maker_fk", "name", "sku", "year") values(2,1,'Empada','SM002',2008);

insert into "car_model"("id", "maker_fk", "name", "sku", "year") values(4,2,'BWM-100','BWM100',2008);
insert into "car_model"("id", "maker_fk", "name", "sku", "year") values(5,2,'BWM-200','BWM200',2009);
insert into "car_model"("id", "maker_fk", "name", "sku", "year") values(6,2,'BWM-300','BWM300',2008);
