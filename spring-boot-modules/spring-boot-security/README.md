## Spring Boot Security

This module contains articles about Spring Boot Security

### Relevant Articles:

- [Spring Boot Security Auto-Configuration](https://www.baeldung.com/spring-boot-security-autoconfiguration)
- [Spring Security for Spring Boot Integration Tests](https://www.baeldung.com/spring-security-integration-tests)
- [Disable Security for a Profile in Spring Boot](https://www.baeldung.com/spring-security-disable-profile)
- [Spring Security – Configuring Different URLs](https://www.baeldung.com/spring-security-configuring-urls)

### Spring Boot Security Auto-Configuration

- mvn clean install 
- uncomment actuator dependency simultaneously with the line from basic auth main class
- uncomment security properties for easy testing. If not random will be generated.

### CURL commands

- curl -X POST -u baeldung-admin:baeldung -d grant_type=client_credentials -d username=baeldung-admin -d password=baeldung http://localhost:8080/oauth/token
