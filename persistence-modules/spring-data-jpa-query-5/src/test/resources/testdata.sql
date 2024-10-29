CREATE TABLE IF NOT EXISTS wearables (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price DECIMAL(10, 2),
    sensor_type VARCHAR(255),
    popularity_index INT
);

DELETE FROM wearables;

INSERT INTO wearables (id, name, price, sensor_type, popularity_index)
VALUES (1, 'SensaWatch', '500.00', 'Accelerometer', 5);

INSERT INTO wearables (id, name, price, sensor_type, popularity_index)
VALUES (2, 'SensaBelt', '300.00', 'Heart Rate', 3);

INSERT INTO wearables (id, name, price, sensor_type, popularity_index)
VALUES (3, 'SensaTag', '120.00', 'Proximity', 2);

INSERT INTO wearables (id, name, price, sensor_type, popularity_index)
VALUES (4, 'SensaShirt', '150.00', 'Human Activity Recognition', 2);