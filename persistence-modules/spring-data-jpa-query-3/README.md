## Spring Data JPA - Query

This module contains articles about querying data using Spring Data JPA.

### Relevant Articles:
- [JPA and Hibernate – Criteria vs. JPQL vs. HQL Query](https://www.baeldung.com/jpql-hql-criteria-query)
- [NonUniqueResultException in Spring Data JPA](https://www.baeldung.com/spring-jpa-non-unique-result-exception)
- [Spring Data Repositories – Collections vs. Stream](https://www.baeldung.com/spring-data-collections-vs-stream)
- [@Query Definitions With SpEL Support in Spring Data JPA](https://www.baeldung.com/spring-data-query-definitions-spel)
- More articles: [[<-- prev]](../spring-data-jpa-query-2)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistance -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 
