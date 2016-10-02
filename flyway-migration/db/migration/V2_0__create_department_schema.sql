CREATE TABLE IF NOT EXISTS `department` (

`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` varchar(20)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8; 

ALTER TABLE `employee` ADD `dept_id` int AFTER `email`;