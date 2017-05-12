insert into users (username, password, enabled) 
  values ('dbuser', 'pass', true);
insert into authorities(username, authority) 
  values ('dbuser', 'ROLE_USER');

insert into users (username, password, enabled) 
  values ('dbguest', 'guest', true);
insert into authorities (username, authority) 
  values ('dbguest', 'ROLE_GUEST');
