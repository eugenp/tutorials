## Setup DDD Hexagonal Spring Application

To run this project, follow these steps:

* Run the application database by executing `docker-compose up` in this directory.
* Launch the Spring Boot Application (DomainLayerApplication). 
* By default, the application will connect to the one of the two databases (configuration in *ddd-layers.properties*)
     * check `CassandraDbOrderRepository.java` and `MongoDbOrderRepository.java` 
     * switch between the databases by making one of the above beans primary using the `@Primary` annotation  