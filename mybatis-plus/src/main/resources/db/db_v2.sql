alter table account
    modify column id varchar(32);

alter table account
    add column type varchar(32);