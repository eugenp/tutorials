## Spring REST Query Language

This module contains articles about the REST query language with Spring

### Courses

The "REST With Spring" Classes: http://bit.ly/restwithspring

The "Learn Spring Security" Classes: http://github.learnspringsecurity.com

### Relevant Articles: 

- [REST Query Language with Spring and JPA Criteria](https://www.baeldung.com/rest-search-language-spring-jpa-criteria)
- [REST Query Language with Spring Data JPA Specifications](https://www.baeldung.com/rest-api-search-language-spring-data-specifications)
- [REST Query Language with Spring Data JPA and QueryDSL](https://www.baeldung.com/rest-api-search-language-spring-data-querydsl)
- [REST Query Language – Advanced Search Operations](https://www.baeldung.com/rest-api-query-search-language-more-operations)
- [REST Query Language with RSQL](https://www.baeldung.com/rest-api-search-language-rsql-fiql)
- [REST Query Language – Implementing OR Operation](https://www.baeldung.com/rest-api-query-search-or-operation)

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

