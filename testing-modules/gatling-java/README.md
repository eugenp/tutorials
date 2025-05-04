### Relevant Articles:

- [Load Testing Rest Endpoint Using Gatling](https://www.baeldung.com/gatling-load-testing-rest-endpoint)
- [How to Display a Full HTTP Response Body With Gatling](https://www.baeldung.com/java-gatling-show-response-body)

### Running a simualtion

To run the simulations from command prompt use `mvn gatling:test`. This will trigger all 3 simulations: EmployeeRegistrationSimulation, FetchSinglePostSimulation and FetchSinglePostSimulationLog.

For executing any other simulations, use `mvn gatling:test -Dgatling.simulationClass=org.baeldung.FastEndpointSimulation`
