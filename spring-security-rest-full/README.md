=========

## REST Example Project with Spring Security


### Relevant Articles: 
- [Spring Security Expressions - hasRole Example](http://www.baeldung.com/spring-security-expressions-basic)
- [REST Pagination in Spring](http://www.baeldung.com/2012/01/18/rest-pagination-in-spring/)
- [HATEOAS for a Spring REST Service](http://www.baeldung.com/2011/11/13/rest-service-discoverability-with-spring-part-5/)
- [REST API Discoverability and HATEOAS](2011/11/06/restful-web-service-discoverability-part-4/)


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
