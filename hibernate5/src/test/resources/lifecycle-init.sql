create sequence hibernate_sequence start with 1 increment by 1;

create table Football_Player (
  id bigint not null,
  club varchar(255),
  name varchar(255),
  primary key (id)
);

insert into
    Football_Player
        (club, name, id)
    values
        ('Juventus', 'Cristiano Ronaldo', next value for hibernate_sequence);

insert into
    Football_Player
        (club, name, id)
    values
        ('Barcelona', 'Lionel Messi', next value for hibernate_sequence);

insert into
    Football_Player
        (club, name, id)
    values
        ('PSG', 'Gigi Buffon', next value for hibernate_sequence);