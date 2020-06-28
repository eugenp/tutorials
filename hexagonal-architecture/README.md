# Hexagonal Architecture
A quick and practical example of Hexagonal Architecture using Spring boot.

This application is using h2 database,which can be accessible http:/localhost:8080/h2

Main Application schema : hexagonal

Test Application Schema : hexagonal_test

1. Rest Api : execute [App](https://github.com/akeshri/tutorials/blob/master/hexagonal-architecture/src/main/java/com/baeldung/hexagonal/architecture/App.java)
      - Get All products  : http://localhost:8080/api/v1/product/all
      - Get product by id : http://localhost:8080/api/v1/product/{productId}
      - Add a product     : http://localhost:8080/api/v1/product/add                                                                  
      For more detail refer [ProductController](https://github.com/akeshri/tutorials/blob/master/hexagonal-architecture/src/main/java/com/baeldung/hexagonal/architecture/controller/ProductController.java)

2. Batch processing : We need to configure active profile as batch i.e. -Dspring.profiles.active=batch and execute [ConsoleApp](https://github.com/akeshri/tutorials/blob/master/hexagonal-architecture/src/main/java/com/baeldung/hexagonal/architecture/ConsoleApp.java)
3. Test case : [ProductServiceTest](https://github.com/akeshri/tutorials/blob/master/hexagonal-architecture/src/test/java/com/baeldung/hexagonal/architecture/service/ProductServiceTest.java)
