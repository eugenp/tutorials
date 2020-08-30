create table Game (id bigint not null, name varchar(255), primary key (id));
create table Player (id bigint not null, name varchar(255), game_id bigint, primary key (id));
alter table Player add constraint FKohr86afuapoujklti79wo27aa foreign key (game_id) references Game(id);