## Spring Security LDAP

This module contains articles about Spring Security LDAP

### The Course

The "Learn Spring Security" Classes: http://github.learnspringsecurity.com

### Relevant Article: 

- [Intro to Spring Security LDAP](https://www.baeldung.com/spring-security-ldap)
- [Spring LDAP Overview](https://www.baeldung.com/spring-ldap)
- [Guide to Spring Data LDAP](https://www.baeldung.com/spring-data-ldap)

### Notes

- the project uses Spring Boot - simply run 'SampleLDAPApplication.java' to start up Spring Boot with a Tomcat container and embedded LDAP server.
- Once started, open 'http://localhost:8080'
- This will display the publicly available Home Page
- Navigate to 'Secure Page' to trigger authentication
- Username: 'baeldung', password: 'password'
- This will authenticate you, and display your allocated roles (as defined in the 'users.ldif' file)

