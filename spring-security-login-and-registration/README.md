=========

## Login and Registration Example Project with Spring Security


### Relevant Articles: 
- [Spring Security Registration Tutorial](http://www.baeldung.com/spring-security-registration)



### Build the Project
```
mvn clean install
```


### Set up MySQL
```
mysql -u root -p 
> CREATE USER 'tutorialuser'@'localhost' IDENTIFIED BY 'tutorialmy5ql';
> GRANT ALL PRIVILEGES ON *.* TO 'tutorialuser'@'localhost';
> FLUSH PRIVILEGES;
```


### Set up Email

You need to configure the email by renaming file "email.properties.sample" to "email.properties" and provide your own username and password.
You also need to use your own host, you can use Amazon or Google for example.
