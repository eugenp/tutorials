### Spring Boot Security Auto-Configuration

- mvn clean install 
- uncomment in application.properties spring.profiles.active=basic # for basic auth config
- uncomment in application.properties spring.profiles.active=form # for form based auth config
- uncomment actuator dependency simultaneously with the line from main class
