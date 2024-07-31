create sequence hibernate_sequence start with 1 increment by 1;

create table Football_Player (
  id bigint not null,
  name varchar(255),
  primary key (id)
);

insert into
    Football_Player
        (name, id)
    values
        ('Cristiano Ronaldo', next value for hibernate_sequence);

insert into
    Football_Player
        (name, id)
    values
        ('Lionel Messi', next value for hibernate_sequence);

insert into
    Football_Player
        (name, id)
    values
        ('Gigi Buffon', next value for hibernate_sequence);