drop table if exists car_maker;
drop table if exists car_model;

--
-- Car makers table
--
create table car_maker( 
  id identity, 
  name varchar(128) not null
);

create unique index ui_car_maker_01 on car_maker(name);

--
-- Car models table
-- 
create table car_model( 
  id identity, 
  maker_fk int not null, 
  name varchar(128) not null, 
  sku varchar(128) not null, 
  year int not null 
);

create unique index ui_car_model_01 on car_model(maker_fk,sku);
create unique index ui_car_model_02 on car_model(maker_fk,name,year);

