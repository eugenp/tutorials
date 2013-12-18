=========

## REST Example Project with Spring Security


### Relevant Articles: 
- [Spring Security Expressions � hasRole Example](http://www.baeldung.com/spring-security-expressions-basic)


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


### Use the REST Service

```
curl http://localhost:8080/spring-security-rest-full/foos
```
