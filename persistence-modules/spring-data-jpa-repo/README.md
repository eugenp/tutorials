## Spring Data JPA - Repo

This module contains articles about repositories in Spring Data JPA 

### Relevant Articles: 
- [Case Insensitive Queries with Spring Data Repository](https://www.baeldung.com/spring-data-case-insensitive-queries)
- [Derived Query Methods in Spring Data JPA Repositories](https://www.baeldung.com/spring-data-derived-queries)
- [LIKE Queries in Spring JPA Repositories](https://www.baeldung.com/spring-jpa-like-queries)
- [Spring Data – CrudRepository save() Method](https://www.baeldung.com/spring-data-crud-repository-save)
- [Spring Data JPA – Adding a Method in All Repositories](https://www.baeldung.com/spring-data-jpa-method-in-all-repositories)
- [Spring Data Composable Repositories](https://www.baeldung.com/spring-data-composable-repositories)
- [Spring Data JPA Repository Populators](https://www.baeldung.com/spring-data-jpa-repository-populators)
- [Calling Stored Procedures from Spring Data JPA Repositories](https://www.baeldung.com/spring-data-jpa-stored-procedures)
- More articles: [[--> next]](/spring-data-jpa-repo-2/)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistance -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 
