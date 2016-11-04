=========

## Spring JPA Example Project


### Relevant Articles: 
- [Spring 3 and JPA with Hibernate](http://www.baeldung.com/2011/12/13/the-persistence-layer-with-spring-3-1-and-jpa/)
- [Transactions with Spring 3 and JPA](http://www.baeldung.com/2011/12/26/transaction-configuration-with-jpa-and-spring-3-1/)
- [The DAO with JPA and Spring](http://www.baeldung.com/spring-dao-jpa)
- [JPA Pagination](http://www.baeldung.com/jpa-pagination)
- [Sorting with JPA](http://www.baeldung.com/jpa-sort)
- [Spring JPA – Multiple Databases](http://www.baeldung.com/spring-data-jpa-multiple-databases)
- [Hibernate Second-Level Cache](http://www.baeldung.com/hibernate-second-level-cache)
- [Spring, Hibernate and a JNDI Datasource](http://www.baeldung.com/spring-persistence-jpa-jndi-datasource)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistance -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 

