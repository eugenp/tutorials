## Building

To build the module, use Maven's `package` goal:

```
mvn clean package
```

The `war` file will be available at `target/deep-jsf.war`

## Running

The `war` application is deployed to a Java EE 7 compliant application server, for example, to GlassFish 4 or later.

The example then will be accessible at http://localhost:8080/deep-jsf/index.xhtml