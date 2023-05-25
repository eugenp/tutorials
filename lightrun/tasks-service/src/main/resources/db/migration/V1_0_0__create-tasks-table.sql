CREATE TABLE tasks (
    task_id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    assigned_to VARCHAR(36) NULL,
    status VARCHAR(20) NOT NULL
);

INSERT INTO tasks(task_id, title, created_at, created_by, assigned_to, status) VALUES
    ('createdemoapplication1', 'Create demo applications - Tasks', '2022-05-05 12:34:56', 'baeldung', 'coxg', 'IN_PROGRESS'),
    ('createdemoapplication2', 'Create demo applications - Users', '2022-05-05 12:34:56', 'baeldung', NULL, 'PENDING'),
    ('createdemoapplication3', 'Create demo applications - API', '2022-05-05 12:34:56', 'baeldung', NULL, 'PENDING');