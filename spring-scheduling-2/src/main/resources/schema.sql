CREATE TABLE IF NOT EXISTS cron_config (
    id BIGINT PRIMARY KEY,
    cron_expression VARCHAR(255) NOT NULL
);
