This application provides an example of how the Hexagonal Architecture can be implemented. In this application, we have
separated our core business logic from other dependencies.

Maintainability
----------------
This increases the maintainability as the core can be
implemented independently with out considering the worried of the infrastructure or other dependencies. This gives an
advantage where multiple teams can work on different aspects of the application.

Technical Debt
--------------
With this architectural implementation, we can achieve less technical debt as the ports exposed are not dependent on the
frameworks which we are going to use. This ports can be implemented with different behaviors as adapters.
