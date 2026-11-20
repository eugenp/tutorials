CREATE TABLE IF NOT EXISTS tb_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    age INT,
    status VARCHAR(20),
    created_at TIMESTAMP
);
