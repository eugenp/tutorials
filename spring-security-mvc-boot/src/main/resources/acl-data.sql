INSERT INTO system_message(id,content) VALUES (1,'First Level Message');
INSERT INTO system_message(id,content) VALUES (2,'Second Level Message');
INSERT INTO system_message(id,content) VALUES (3,'Third Level Message');

INSERT INTO acl_class (id, class) VALUES
(1, 'org.baeldung.acl.persistence.entity.NoticeMessage');

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'manager'),
(2, 1, 'hr'),
(3, 1, 'admin'),
(4, 0, 'ROLE_EDITOR');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 3, 0),
(2, 1, 2, NULL, 3, 0),
(3, 1, 3, NULL, 3, 0)
;

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 2, 1, 2, 1, 1, 1),
(3, 1, 3, 4, 1, 1, 1, 1),
(4, 2, 1, 2, 1, 1, 1, 1),
(5, 2, 2, 4, 1, 1, 1, 1),
(6, 3, 1, 4, 1, 1, 1, 1),
(7, 3, 2, 4, 2, 1, 1, 1)
;