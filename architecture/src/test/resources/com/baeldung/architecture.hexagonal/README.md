## Setup Hexagonal Architecture Spring Application

To run this project, follow these steps:

* Run the application database by executing `docker-compose up` in this directory.
* Launch the Spring Boot Application (QuoteApplication). 
* By default, the application will connect to the one of the two sources (configuration in *architecture-hexagonal.properties*)
     * check `HardcodedQuoteRepository.java` and `MongoDbQuoteRepository.java` 
     * switch between the sources by making one of the above beans primary using the `@Primary` annotation  
