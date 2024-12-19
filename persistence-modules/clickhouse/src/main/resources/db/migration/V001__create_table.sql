CREATE TABLE authors (
    id UUID,
    name String,
    email String,
    created_at DateTime
)
ENGINE = MergeTree()
PRIMARY KEY id;