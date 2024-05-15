INSERT INTO acl_sid (principal, sid) VALUES
(1, 'manager'),
(1, 'hr'),
(0, 'ROLE_EDITOR');

INSERT INTO acl_class (id, class) VALUES
(1, 'com.baeldung.acl.persistence.entity.NoticeMessage');

INSERT INTO system_message(id,content) VALUES 
(1,'First Level Message'),
(2,'Second Level Message'),
(3,'Third Level Message');

INSERT INTO acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, NULL, 3, 0),
(1, 2, NULL, 3, 0),
(1, 3, NULL, 3, 0);

INSERT INTO acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1),
(1, 2, 1, 2, 1, 1, 1),
(1, 3, 3, 1, 1, 1, 1),
(2, 1, 2, 1, 1, 1, 1),
(2, 2, 3, 1, 1, 1, 1),
(3, 1, 3, 1, 1, 1, 1),
(3, 2, 3, 2, 1, 1, 1);