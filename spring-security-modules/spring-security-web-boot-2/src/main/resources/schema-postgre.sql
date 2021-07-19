DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  username varchar(50) NOT NULL PRIMARY KEY,
  password varchar(100) NOT NULL,
  enabled boolean not null DEFAULT true
);
  
CREATE TABLE authorities (
  username varchar(50) NOT NULL,
  authority varchar(50) NOT NULL,
  CONSTRAINT foreign_authorities_users_1 foreign key(username) references users(username)
);

CREATE UNIQUE INDEX ix_auth_username on authorities (username,authority);