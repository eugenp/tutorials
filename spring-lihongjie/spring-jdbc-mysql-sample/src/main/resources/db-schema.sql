create table if not exists current_states (id smallint primary key, state_code char(2), name varchar(50));
insert into current_states(id, state_code, name) values(1, 'MA', 'Massachusetts') ON DUPLICATE KEY UPDATE id=id;
insert into current_states(id, state_code, name) values(2, 'NH', 'New Hampshire') ON DUPLICATE KEY UPDATE id=id;
insert into current_states(id, state_code, name) values(3, 'ME', 'Maine') ON DUPLICATE KEY UPDATE id=id;
insert into current_states(id, state_code, name) values(4, 'VT', 'Vermont') ON DUPLICATE KEY UPDATE id=id;
