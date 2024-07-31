CREATE USER 'test_user'@'%' IDENTIFIED BY 'Password2022' require X509;
GRANT ALL PRIVILEGES ON test_db.* TO 'test_user'@'%';