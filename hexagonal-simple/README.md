# The Simple way to work with Hexagonal Architecture


We divided this application into six maven projects: Domain, Rest, Port, Principal, Persistence, and Hexagonal-Example. Its objective is explicit about that architecture. There are other approaches. The division into six projects is to explain the separation of the responsibilities.

The API can be called by http://localhost:8080/timesheet
In this resource, there is a call with a post method.
