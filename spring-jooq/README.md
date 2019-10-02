## Spring jOOQ

This module contains articles about Spring with jOOQ

### Relevant Articles:
- [Spring Boot Support for jOOQ](http://www.baeldung.com/spring-boot-support-for-jooq)
- [Introduction to jOOQ with Spring](http://www.baeldung.com/jooq-with-spring)

In order to fix the error "Plugin execution not covered by lifecycle configuration: org.jooq:jooq-codegen-maven:3.7.3:generate (execution: default, phase: generate-sources)", right-click on the error message and choose "Mark goal generated as ignore in pom.xml". Until version 1.4.x, the maven-plugin-plugin was covered by the default lifecycle mapping that ships with m2e.

Since version 1.5.x, the m2e default lifecycle mapping no longer covers the maven-plugin-plugin.
