--------------------------------- CustomerOrderApp installation steps----------------------------------

1. Clone or download sample project from GitHub repo: https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/customer-order-app
2. Unzip project folder to local disk for example to: C:/baeldung-tutorials/customer-order-app
3. Run `mvn clean install -DskipTests=true`
4. Navigate to payment-service/payment-server module folder and type `mvn spring-boot:run`
5. Open another CMD PROMPT window.
   Navigate to order-service/order-server module folder and type `mvn spring-boot:run`
6. Open another CMD PROMPT window. 
   Navigate to customer-service module folder and type `mvn spring-boot:run`

7. Launch the Postman application from your machine and import the collection of POST requests located in the _postman_ folder

