1. Overview

In this Spring project, we have explained Hexagonal Architecture in Java through a practical example.

2. What is Hexagonal Architecture?

In simple words, a Hexagonal architecture separates software in two components - inside and outside, instead of conceptual layers.
The component that remains inside usually consists of application and domain layers, along with the core business logic. Whereas, the component for the outside world consists of layers like UI and Database.

The connection between the inside and outside components realises via abstractions called ports and implementations called adapters.

3. Benefits

Flexibility - with different adapters, the software can serve multiple channels
Testability - as mocking code is easy
Purity - as the core logic remains intact

4. How to test the application : Shopify

4.1. To add new items into shopping cart
 POST : http://localhost:8080/shopify
      body : {
                "productId" : "2",
                "name" : "rice",
                "price" : "300"
             }
4.2. To get all the items present in the cart
GET : http://localhost:8080/shopify
