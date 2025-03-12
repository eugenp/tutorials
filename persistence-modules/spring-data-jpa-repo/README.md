## Spring Data JPA - Repo

This module contains articles about repositories in Spring Data JPA 

### Relevant Articles:
- [Spring Data – CrudRepository save() Method](https://www.baeldung.com/spring-data-crud-repository-save)
- [How to Access EntityManager with Spring Data](https://www.baeldung.com/spring-data-entitymanager)
- [LIKE Queries in Spring JPA Repositories](https://www.baeldung.com/spring-jpa-like-queries)
- [Storing PostgreSQL JSONB Using Spring Boot and JPA](https://www.baeldung.com/spring-boot-jpa-storing-postgresql-jsonb)
- [“Not a Managed Type” Exception in Spring Data JPA](https://www.baeldung.com/spring-data-jpa-not-managed-type-exception)
- [Performance Difference Between save() and saveAll() in Spring Data](https://www.baeldung.com/spring-data-save-saveall)
- [Calling Stored Procedures from Spring Data JPA Repositories](https://www.baeldung.com/spring-data-jpa-stored-procedures)
- [Correct Use of flush() in JPA](https://www.baeldung.com/spring-jpa-flush)

- More articles: [[--> next]](../spring-data-jpa-repo-2)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistence -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 
