### Relevant Articles: 
- [Limiting Query Results with JPA and Spring Data JPA](https://www.baeldung.com/jpa-limit-query-results)
- [Sorting Query Results with Spring Data](https://www.baeldung.com/spring-data-sorting)
- [INSERT Statement in JPA](https://www.baeldung.com/jpa-insert)
- [Pagination and Sorting using Spring Data JPA](https://www.baeldung.com/spring-data-jpa-pagination-sorting)
- [Spring Data JPA Query by Example](https://www.baeldung.com/spring-data-query-by-example)
- [DB Integration Tests with Spring Boot and Testcontainers](https://www.baeldung.com/spring-boot-testcontainers-integration-test)
- [Spring Data JPA @Modifying Annotation](https://www.baeldung.com/spring-data-jpa-modifying-annotation)
- [Spring Data JPA Batch Inserts](https://www.baeldung.com/spring-data-jpa-batch-inserts)
- [Batch Insert/Update with Hibernate/JPA](https://www.baeldung.com/jpa-hibernate-batch-insert-update)
- [Difference Between save() and saveAndFlush() in Spring Data JPA](https://www.baeldung.com/spring-data-jpa-save-saveandflush)

- [Programmatic Transaction Management in Spring](https://www.baeldung.com/spring-programmatic-transaction-management)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistance -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 
