**A quick and Practical Example of Hexagonal Architecture in Java**

The Application is a Rest Controller using Spring Boot Framework.

There are 2 major components
1. Domain: Consisting of Core App models and service ports.
2. Outer Layer - consisting of Repository - which can have multiple implementation, and the
Rest Controller that is implementing the core functionality.