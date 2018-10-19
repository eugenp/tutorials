## Building

To build the module, use Maven's `package` goal:

```
mvn clean package
```

The `war` file will be available at `target/remote-debugging.war`

## Running

The `war` application is deployed to Apache Tomcat 8 or any other Java Web or Application server. 
To deploy it to the Apache Tomcat 8 server, drop it in the `tomcat/webapps` directory.

The service then will be accessible at http://localhost:8080/remote-debugging/hello?name=John