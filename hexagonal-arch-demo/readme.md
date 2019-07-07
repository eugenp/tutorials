## Demo project to explain Hexagonal Architecture
The project comprises of 6 maven modules. Each explained below
### core
Contains domain model, business rules that define the business objects.
### port
Primary ports for application tasks that can be invoked by external actors. Includes dependencies to secondary ports and adapters.
### secondary-port
Secondary ports that the application invokes to perform internal tasks.
### rest-adapter
Primary interface for the application.
### jpa-adapter
Secondary interface containing implementation for secondary ports. This module has implementaion for persistence needs of the application.
### smtp-adapter
Secondary interface containing implementation for secondary ports. This module has implementaion for email requirements of the application.
### kafka-adapter
Secondary interface containing implementation for secondary ports. This module has implementaion for messaging needs of the application.
