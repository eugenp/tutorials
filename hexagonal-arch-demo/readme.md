## Demo project to explain Hexagonal Architecture
The project comprises of following igh level packages as explained below.
### core
Contains domain model, business rules that define the business objects.
### port.primary
Primary ports for application tasks that can be invoked by external actors.
### port.secondary
Secondary ports that the application invokes to perform internal tasks.
### adapter.primary.rest
Primary REST interface to the application and adapters.
### adapter.secondary.persistence
Secondary interface for persistence needs of the application. 

