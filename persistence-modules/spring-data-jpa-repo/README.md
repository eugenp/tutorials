## Spring Data JPA - Repo

This module contains articles about repositories in Spring Data JPA 

### Relevant Articles:
- [Spring Data – CrudRepository save() Method](https://www.baeldung.com/spring-data-crud-repository-save)
- [How to Access EntityManager with Spring Data](https://www.baeldung.com/spring-data-entitymanager)
- [LIKE Queries in Spring JPA Repositories](https://www.baeldung.com/spring-jpa-like-queries)
- [Performance Difference Between save() and saveAll() in Spring Data](https://www.baeldung.com/spring-data-save-saveall)

- More articles: [[--> next]](../spring-data-jpa-repo-2)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistence -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 
