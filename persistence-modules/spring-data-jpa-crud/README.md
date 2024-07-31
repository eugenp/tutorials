## Spring Data JPA - CRUD

This module contains articles about CRUD operations in Spring Data JPA 

### Relevant Articles: 
- [Spring Data JPA – Derived Delete Methods](https://www.baeldung.com/spring-data-jpa-deleteby)
- [Spring Data JPA Delete and Relationships](https://www.baeldung.com/spring-data-jpa-delete)
- [INSERT Statement in JPA](https://www.baeldung.com/jpa-insert)
- [Spring Data JPA Batch Inserts](https://www.baeldung.com/spring-data-jpa-batch-inserts)
- [Batch Insert/Update with Hibernate/JPA](https://www.baeldung.com/jpa-hibernate-batch-insert-update)
- [Difference Between save() and saveAndFlush() in Spring Data JPA](https://www.baeldung.com/spring-data-jpa-save-saveandflush)
- [How to Implement a Soft Delete with Spring JPA](https://www.baeldung.com/spring-jpa-soft-delete)
- More articles: [[next-->]](/persistence-modules/spring-data-jpa-crud-2)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistance -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 
