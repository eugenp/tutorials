CREATE TABLE IF NOT EXISTS USERS
(
  `id`         int AUTO_INCREMENT NOT NULL,
  `first_name` varchar(100)       NOT NULL,
  `last_name`  varchar(100)       NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS ITEMS
(
  `id`       int AUTO_INCREMENT NOT NULL,
  `title`    varchar(100)       NOT NULL,
  `produced` date,
  `price`    float,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS PURCHASES
(
  `id`          int     NOT NULL AUTO_INCREMENT,
  `id_user`     int     NOT NULL,
  `id_item`     int     NOT NULL,
  `total_price` float   NOT NULL,
  `quantity`    int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id_user`) REFERENCES USERS (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`id_item`) REFERENCES ITEMS (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
