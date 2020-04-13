# user-github-services

## Points to Consider
- As this is non-production environment we can use Basic authentication to invoke Github services using [Personal access tokens](https://github.com/settings/tokens)
- Just for simplicity, the maven dependency spring-security-oauth2 has been disabled

## Token Generation
Generate the token through Personal access token and pass as an VM argument when trying to **run the application**

## Build 
mvn clean install

## Run the application
java -Dgithub.token=<--pass the token--> -jar target/app.jar

Eventhough the VM argument is not passed the application can invoke the Github services but the ratelimit will be in effect

## APIs
All the APIs are saved as a POSTMAN collection and the same JSON file has been committed to the repository.
