# Spring Data Snowflake

Before running the project please update the application.properties under src/main/resources with your snowflake connection details
```
spring.datasource.url=jdbc:snowflake://yourcompany.snowflakecomputing.com/?db=DB_NAME&warehouse=WAREHOUSE_NAME&CLIENT_SESSION_KEEP_ALIVE=true&schema=SCHEMA_NAME
spring.datasource.username=USER_NAME
spring.datasource.password=USER_PWD
```

### In Snowflake create below to test 
```sql
CREATE TABLE USER (ID NUMBER(4,0), NAME VARCHAR(25));
INSERT INTO USER VALUES (1, 'Ryan');
INSERT INTO USER VALUES (2, 'Matt');
```

### Repository
There is no dialect available for snowflake so we need extend default hibernate dialect and specify in hibernate properties

Queries need to map the response to field names, default jpa generated queries won't map to the field directly.

For example if you  have field names like below class.
```java
public class User {
    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_STATUS")
    private String userStatus;
}
```

Query field mapping should be specified like below
```java
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT USER_ID as \"user_id\", USER_NAME as \"user_name\", USER_STATUS as \"user_status\" FROM USER WHERE USER_ID = ?", nativeQuery = true)
    User findByUserId(Long userId);
}
```


### Running Locally

To build and run with maven do the following:

```
mvn clean install
mvn spring-boot:run
```

### Testing Local
```
http://localhost:8080/user/{id}

http://localhost:8080/user/all
```

### Snowflake references
https://docs.snowflake.net/manuals/user-guide-intro.html


