/*USE shard1;
DROP TABLE IF EXISTS `s_usr`;
CREATE  TABLE `s_usr` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
USE shard2;
DROP TABLE IF EXISTS `s_usr`;
CREATE  TABLE `s_usr0` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;*/

USE shard1;
DROP TABLE IF EXISTS `s_usr0`;
CREATE  TABLE `s_usr0` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr1`;
CREATE  TABLE `s_usr1` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr2`;
CREATE  TABLE `s_usr2` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr3`;
CREATE  TABLE `s_usr3` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr4`;
CREATE  TABLE `s_usr4` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr5`;
CREATE  TABLE `s_usr5` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr6`;
CREATE  TABLE `s_usr6` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr7`;
CREATE  TABLE `s_usr7` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr8`;
CREATE  TABLE `s_usr8` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr9`;
CREATE  TABLE `s_usr9` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

USE shard2;
DROP TABLE IF EXISTS `s_usr0`;
CREATE  TABLE `s_usr0` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr1`;
CREATE  TABLE `s_usr1` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr2`;
CREATE  TABLE `s_usr2` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr3`;
CREATE  TABLE `s_usr3` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr4`;
CREATE  TABLE `s_usr4` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr5`;
CREATE  TABLE `s_usr5` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr6`;
CREATE  TABLE `s_usr6` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr7`;
CREATE  TABLE `s_usr7` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr8`;
CREATE  TABLE `s_usr8` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `s_usr9`;
CREATE  TABLE `s_usr9` (
  `usr_kid` int not null,
  `usr_name` VARCHAR(50) NOT NULL,
  `usr_gender` VARCHAR(10),
  `usr_country` VARCHAR(20),
  PRIMARY KEY(`usr_kid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
