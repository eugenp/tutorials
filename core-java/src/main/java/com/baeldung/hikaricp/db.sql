CREATE TABLE IF NOT EXISTS user_auth_details (
	username VARCHAR(20) PRIMARY KEY,
	password VARCHAR(60) NOT NULL,
	role VARCHAR(20) NOT NULL,
	mail VARCHAR(255)
);

INSERT INTO user_auth_details VALUES ( 'admin1', '123', 'ADMIN', 'email1@email.com' );
INSERT INTO user_auth_details VALUES ( 'admin2', '123', 'ADMIN', 'email2@email.com' );
INSERT INTO user_auth_details VALUES ( 'admin3', '123', 'ADMIN', 'email3@email.com' );

COMMIT;