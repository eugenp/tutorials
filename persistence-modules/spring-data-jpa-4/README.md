### Relevant Articles: 
- [Derived Query Methods in Spring Data JPA Repositories](https://www.baeldung.com/spring-data-derived-queries)
- [LIKE Queries in Spring JPA Repositories](https://www.baeldung.com/spring-jpa-like-queries)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistance -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 
