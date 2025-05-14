## Spring Boot Security

This module contains code about Spring Boot Security

### Spring Boot Security Auto-Configuration

- mvn clean install 
- uncomment actuator dependency simultaneously with the line from basic auth main class
- uncomment security properties for easy testing. If not random will be generated.

### CURL commands

- curl -X POST -u baeldung-admin:baeldung -d grant_type=client_credentials -d username=baeldung-admin -d password=baeldung http://localhost:8080/oauth/token
