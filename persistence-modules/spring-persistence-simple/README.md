=========

## Spring Persistence Example Project


### Relevant Articles: 
- [A Guide to JPA with Spring](https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa)
- [Bootstrapping Hibernate 5 with Spring](http://www.baeldung.com/hibernate-5-spring)
- [The DAO with Spring and Hibernate](https://www.baeldung.com/persistence-layer-with-spring-and-hibernate)
- [Simplify the DAO with Spring and Java Generics](https://www.baeldung.com/simplifying-the-data-access-layer-with-spring-and-java-generics)
- [Transactions with Spring and JPA](https://www.baeldung.com/transaction-configuration-with-jpa-and-spring)
- [Introduction to Spring Data JPA](http://www.baeldung.com/the-persistence-layer-with-spring-data-jpa)
- [Spring Data JPA @Query](http://www.baeldung.com/spring-data-jpa-query)
- [Spring JDBC](https://www.baeldung.com/spring-jdbc-jdbctemplate)
- [Transaction Propagation and Isolation in Spring @Transactional](https://www.baeldung.com/spring-transactional-propagation-isolation)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistance -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 

