create table IF NOT EXISTS system_message (id integer not null, content varchar(255), primary key (id));

CREATE TABLE IF NOT EXISTS acl_sid (
  id bigint NOT NULL AUTO_INCREMENT,
  principal tinyint NOT NULL,
  sid varchar(100) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT unique_uk_1 UNIQUE (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class (
  id bigint NOT NULL AUTO_INCREMENT,
  class varchar(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT unique_uk_2 UNIQUE (class)
);

CREATE TABLE IF NOT EXISTS acl_entry (
  id bigint NOT NULL AUTO_INCREMENT,
  acl_object_identity bigint NOT NULL,
  ace_order int NOT NULL,
  sid bigint NOT NULL,
  mask int NOT NULL,
  granting tinyint NOT NULL,
  audit_success tinyint NOT NULL,
  audit_failure tinyint NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT unique_uk_4 UNIQUE (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
  id bigint NOT NULL AUTO_INCREMENT,
  object_id_class bigint NOT NULL,
  object_id_identity bigint NOT NULL,
  parent_object bigint DEFAULT NULL,
  owner_sid bigint DEFAULT NULL,
  entries_inheriting tinyint NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT unique_uk_3 UNIQUE (object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);