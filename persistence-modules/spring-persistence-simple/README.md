=========

## Spring Persistence Example Project


### Relevant Articles: 
- [A Guide to JPA with Spring](https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa)
- [Bootstrapping Hibernate 5 with Spring](http://www.baeldung.com/hibernate-5-spring)
- [The DAO with Spring and Hibernate](http://www.baeldung.com/persistence-layer-with-spring-and-hibernate)
- [Transactions with Spring and JPA](https://www.baeldung.com/transaction-configuration-with-jpa-and-spring)


### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistance -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 

