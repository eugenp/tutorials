--
-- Sample schema for testing vault database secrets
--
create schema fakebank;
use fakebank;
create table account( 
  id decimal(16,0), 
  name varchar(30),
  branch_id decimal(16,0),
  customer_id decimal(16,0),
  primary key (id));
  
--
-- MySQL user that will be used by Vault to create other users on demand
--
create user 'fakebank-admin'@'%' identified by 'Sup&rSecre7!'
grant all privileges on fakebank.* to 'fakebank-admin'@'%' with grant option;
grant create user on *.* to 'fakebank-admin' with grant option;

flush privileges;
