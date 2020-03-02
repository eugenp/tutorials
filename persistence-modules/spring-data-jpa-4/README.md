### Relevant Articles: 
- [Derived Query Methods in Spring Data JPA Repositories](https://www.baeldung.com/spring-data-derived-queries)
- [LIKE Queries in Spring JPA Repositories](https://www.baeldung.com/spring-jpa-like-queries)
- [A Guide to Spring’s Open Session In View](https://www.baeldung.com/spring-open-session-in-view)
- [Programmatic Transaction Management in Spring](https://www.baeldung.com/spring-programmatic-transaction-management)
- [JPA Entity Lifecycle Events](https://www.baeldung.com/jpa-entity-lifecycle-events)
- [Working with Lazy Element Collections in JPA](https://www.baeldung.com/java-jpa-lazy-collections)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistance -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 
