CREATE TABLE IF NOT EXISTS CLIENTS
(
    `id`         int PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `first_name` varchar(100)       NOT NULL,
    `last_name`  varchar(100)       NOT NULL
);

CREATE TABLE IF NOT EXISTS ITEMS
(
    `id`       int PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `title`    varchar(100)       NOT NULL,
    `produced` date,
    `price`    float
);

CREATE TABLE IF NOT EXISTS PURCHASES
(
    `id`          int   PRIMARY KEY AUTO_INCREMENT  NOT NULL,
    `id_user`     int     NOT NULL,
    `id_item`     int     NOT NULL,
    `total_price` float   NOT NULL,
    `quantity`    int NOT NULL,
    FOREIGN KEY (`id_user`) REFERENCES CLIENTS (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`id_item`) REFERENCES ITEMS (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
