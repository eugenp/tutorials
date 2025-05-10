## Spring Security LDAP

This module contains code about Spring Security LDAP

### The Course

The "Learn Spring Security" Classes: http://github.learnspringsecurity.com

### Notes

- the project uses Spring Boot - simply run 'SampleLDAPApplication.java' to start up Spring Boot with a Tomcat container and embedded LDAP server.
- Once started, open 'http://localhost:8080'
- This will display the publicly available Home Page
- Navigate to 'Secure Page' to trigger authentication
- Username: 'baeldung', password: 'password'
- This will authenticate you, and display your allocated roles (as defined in the 'users.ldif' file)

