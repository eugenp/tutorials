=========

## Spring Persistence Example Project


### Relevant Articles: 
- [Transaction Propagation and Isolation in Spring @Transactional](https://www.baeldung.com/spring-transactional-propagation-isolation)
- [Transactional Annotations: Spring vs. JTA](https://www.baeldung.com/spring-vs-jta-transactional)
- [Test a Mock JNDI Datasource with Spring](https://www.baeldung.com/spring-mock-jndi-datasource)
- [Detecting If a Spring Transaction Is Active](https://www.baeldung.com/spring-transaction-active)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistance -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 

