### Relevant Articles:
- [Intro to Gatling](http://www.baeldung.com/introduction-to-gatling)
- [Run Gatling Tests From Jenkins](https://www.baeldung.com/ops/jenkins-run-gatling-tests)

### Running a simualtion
- To run a simulation use "simulation" profile, command - `mvn install -Psimulation -Dgib.enabled=false`
- To run the default scenario, use `mvn install gatling:test`
- To run specific simulation, use the parameter to specify the class: `mvn install gatling:test -Dgatling.simulation.class=org.baeldung.RecordedSimulation`
