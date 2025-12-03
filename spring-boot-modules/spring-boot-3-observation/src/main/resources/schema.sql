CREATE TABLE IF NOT EXISTS campaign (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    budget DECIMAL(19, 2),
    start_date DATE,
    end_date DATE
);
