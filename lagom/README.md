### Relevant articles

- [Guide to Reactive Microservices Using Lagom Framework](https://www.baeldung.com/lagom-reactive-microservices)




Steps to setup from scratch

1) Create sbt build file "build.sbt" 
2) Create plugins file project/plugins.sbt
3) Create build properties file project/build.properties
4) Run sbt command from project root to generate project directories, generated projects at target/lagom-dynamic-projects
   Service Locator project: lagom-internal-meta-project-service-locator
   cassandra project: lagom-internal-meta-project-cassandra	
5) Create folders in all projects to follow maven like directory structure layout for project source code: src/main/java and src/main/java/resources and test folders.
6) Weather microservice
	a) WeatherService Interface with method:
		weatherStatsForToday()
	b) WeatherServiceImpl to return weatherstats from a random list of weather stats
	
7) Greeting Microservice
	 GreetingService Interface: 
		handleGreetFrom(String user)
		
	a) Reply back to user with greeting message("Hello") along with weather stats fetched from weather microservice
	b) Update the greeting message("Hello Again") for an existing user.
	
8) GreetingEntity: PersistentEntity<GreetingCommand, GreetingEvent, GreetingState>
	a) handles ReceivedGreetingCommand 
	b) Stores ReceivedGreetingEvent events to cassandra 
	c) Processes ReceivedGreetingEvent to update GreetingState("Hello" to "Hello Again") if required.
    
9)  Register modules with lagom using application.conf files.
10) Run project: sbt lagom:runAll
	

Sample Run:
curl http://localhost:9000/api/greeting/Nikhil;
Hello Nikhil! Today's weather stats: Going to be very humid, Take Water

curl http://localhost:9000/api/greeting/Nikhil;
Hello Again Nikhil! Today's weather stats: Going to Rain, Take Umbrella




