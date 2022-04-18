CREATE OR REPLACE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO users (username, password) 
VALUES
	('root', '63a9f0ea7bb98050796b649e85481845'),
	('user_1', '286755fad04869ca523320acce0dc6a4'),
	('user_2', '286755fad04869ca523320acce0dc6a4');

