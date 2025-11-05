CREATE TABLE hogwarts_houses (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    name VARCHAR(50) NOT NULL UNIQUE,
    founder VARCHAR(50) NOT NULL UNIQUE,
    house_colors VARCHAR(50) NOT NULL UNIQUE,
    animal_symbol VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE wizards (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    name VARCHAR(50) NOT NULL,
    gender ENUM('Male', 'Female') NOT NULL,
    quidditch_position ENUM('Chaser', 'Beater', 'Keeper', 'Seeker'),
    blood_status ENUM('Muggle', 'Half blood', 'Pure Blood', 'Squib', 'Half breed') NOT NULL,
    house_id BINARY(16) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT wizard_fkey_house FOREIGN KEY (house_id) REFERENCES hogwarts_houses (id)
);