## Guide to Microservices: with Spring Boot and Spring Cloud Ebook

This module contains code about bootstrapping Spring Cloud applications that are part of the Guide to Microservices: with Spring Boot and Spring Cloud Ebook.

### Running the Project

- First, you need a redis server running on the default port
- To run the project:
  - copy the application-config folder to c:\Users\{username}\ on Windows or /home/{username}/ on *nix. Then open a git bash terminal in application-config and run:
    - git init
    - git add .
    - git commit -m "First commit"
  - start the config server
  - start the discovery server
  - start all the other servers in any order (gateway, zipkin-log-svc-book, zipkin-log-svc-rating, zipkin)
