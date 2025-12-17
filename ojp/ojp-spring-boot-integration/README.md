# OJP Spring Boot Integration Demo

This is a Spring Boot demo application that demonstrates integration with OJP (Open JDBC Proxy) using TestContainers.

## Overview

This application demonstrates:
- A simple REST API for managing books (Create and Read operations)
- Integration with PostgreSQL database through OJP proxy
- Custom TestContainer implementation for OJP
- Integration tests using TestContainers

## Key Components

### Custom OJP TestContainer

The `OjpContainer` class (`src/test/java/com/example/demo/testcontainers/OjpContainer.java`) is a custom TestContainer that:
- Extends `GenericContainer<OjpContainer>`
- Wraps the Docker image `rrobetti/ojp:0.3.0-beta`
- Exposes port 1059 (OJP's default port)
- Provides helper methods to get connection information

### Integration Test

The `BookControllerIntegrationTest` class demonstrates:
- Starting OJP container before PostgreSQL container
- Both containers run on a shared Docker network
- The Spring Boot application connects to OJP, which proxies to PostgreSQL
- Full end-to-end testing of the REST API

## Container Setup

The test uses two containers:
1. **OJP Container**: Started first, acts as a JDBC proxy
2. **PostgreSQL Container**: Started second, hosts the actual database

Both containers share a Docker network to allow inter-container communication.

## Building and Testing

```bash
# Build the project
./mvnw clean compile

# Run tests
./mvnw test
```

## Questions and Concerns

### 1. OJP Container Configuration
**Question**: The OJP container may require additional configuration to properly route connections to PostgreSQL.

**Current Implementation**: The JDBC URL format used is:
```
jdbc:ojp[host:port]_postgresql://postgres_host:postgres_port/database
```

This assumes that:
- The application (running on the host) connects to OJP via the exposed port
- OJP needs to know how to reach PostgreSQL (using the network alias "postgres")

**Potential Issue**: OJP running in a container may not be able to resolve the "postgres" network alias when the connection request comes from outside the Docker network (from the Spring Boot application running on the host).

### 2. Network Architecture
**Current Setup**:
- OJP Container: On Docker network with alias "ojp"
- PostgreSQL Container: On Docker network with alias "postgres"
- Spring Boot App: Running on host machine
- Connection flow: Host → OJP (via exposed port) → PostgreSQL (via network alias)

**Concern**: The OJP JDBC driver URL format may need to be adjusted. The current format assumes OJP can resolve the PostgreSQL network alias, but since the JDBC connection string comes from the Spring Boot application (on the host), this might not work as expected.

### 3. Alternative Approaches to Consider

**Option A**: Run the entire Spring Boot application inside a container
- This would keep everything on the same Docker network
- More consistent with production deployments
- Requires changes to the test setup

**Option B**: Use host.docker.internal or similar
- Configure OJP to connect to PostgreSQL using host networking
- May be platform-dependent (works differently on Mac/Windows/Linux)

**Option C**: Custom OJP configuration
- Pass OJP configuration through environment variables
- Configure PostgreSQL connection details explicitly in OJP

### 4. OJP Documentation Needed
To complete this integration properly, we need:
- OJP's JDBC URL format specification
- How OJP discovers/connects to backend databases
- Whether OJP requires configuration files or environment variables
- OJP's logging/debugging capabilities to troubleshoot connection issues

## Next Steps

1. Review OJP documentation for proper JDBC URL format
2. Test the integration test to see if it works with current setup
3. Adjust container networking if needed
4. Add more detailed logging to troubleshoot any connection issues
5. Consider adding a docker-compose.yml for local development/testing

## Dependencies

- Spring Boot 3.3.5
- Java 17
- TestContainers 1.20.4
- OJP JDBC Driver 0.3.0-beta
- PostgreSQL 16

## License

This is a demo/tutorial project.
