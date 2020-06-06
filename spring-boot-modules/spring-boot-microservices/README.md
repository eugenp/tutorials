--------------------------------- CustomerOrderApp installation steps----------------------------------

1. Clone or download sample project from GitHub repo: https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-microservices
2. Unzip project folder to local disk for example to: C:/baeldung-tutorials/spring-boot-microservices/customer-order-app
3. Run `mvn clean install -DskipTests=true`
4. Navigate to order-service/order-server module folder and type `mvn spring-boot:run`
5. Open another CMD PROMPT window. 
   Navigate to customer-service module folder and type `mvn spring-boot:run`

6. Launch the Postman application from your machine and import the collection located in the _postman_ folder in project root
7. Verify successful request from the POSTMAN to http://localhost:8001/customer-service/order
