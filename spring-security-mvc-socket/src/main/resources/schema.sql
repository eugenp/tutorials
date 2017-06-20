DROP TABLE IF EXISTS user;
CREATE TABLE user (
  user_id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  UNIQUE KEY uni_user_username (username),
  PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS role;
CREATE TABLE role (
  role_id INT(11) NOT NULL AUTO_INCREMENT,
  role VARCHAR(45) NOT NULL,
  UNIQUE KEY uni_role_role (role),
  PRIMARY KEY (role_id)
);

DROP TABLE IF EXISTS pet_detail;
CREATE TABLE pet_detail (
  pet_detail_id INT(11) NOT NULL AUTO_INCREMENT,
  description VARCHAR(100),
  PRIMARY KEY (pet_detail_id)
);

DROP TABLE IF EXISTS pet;
CREATE TABLE pet (
  pet_id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  type VARCHAR(45) NOT NULL,
  pet_detail_id INT(11) NOT NULL,
  PRIMARY KEY (pet_id)
);

/** JOIN TABLES */

DROP TABLE IF EXISTS user_pet;
CREATE TABLE user_pet (
  id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  pet_id INT(11) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role(
  id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  role_id INT(11) NOT NULL,
  PRIMARY KEY (id)
);