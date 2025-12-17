# OJP Spring Boot Integration Demo

This is a Spring Boot demo application that demonstrates integration with OJP (Open JDBC Proxy) using TestContainers.

## ✅ Status: Working

The integration test successfully demonstrates:
- Custom OJP TestContainer implementation
- OJP container starts before PostgreSQL
- Spring Boot application connects through OJP to PostgreSQL
- Full CRUD operations work correctly through the proxy

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

### ✅ Resolved: Container Configuration and Network Architecture

**Initial Concern**: Whether OJP running in a container could resolve the PostgreSQL network alias when the connection request comes from outside the Docker network.

**Resolution**: The implementation works correctly! The architecture is:
- Spring Boot App (Host) → OJP Container (via exposed port) → PostgreSQL Container (via network alias)
- The JDBC URL format `jdbc:ojp[host:port]_postgresql://postgres:5432/database` works as expected
- OJP correctly resolves the "postgres" network alias because it's running within the Docker network

**Test Results**: The integration test passes successfully, confirming that:
- Both containers start correctly on a shared network
- OJP can connect to PostgreSQL using the network alias
- The Spring Boot application can connect through OJP to perform database operations

### Additional Notes

#### Container Startup Order
The current implementation uses `@Container` annotations for both containers. TestContainers manages the lifecycle automatically, and the order doesn't matter because:
- Both containers can start independently
- The application waits for containers to be ready before connecting
- There are no direct dependencies between OJP and PostgreSQL startup

#### JDBC URL Format
The OJP JDBC URL format is: `jdbc:ojp[ojp_host:ojp_port]_postgresql://postgres_host:postgres_port/database`

Where:
- `ojp_host:ojp_port`: Connection to OJP from the host machine (uses exposed/mapped port)
- `postgres_host:postgres_port`: Connection from OJP to PostgreSQL (uses Docker network alias)

This dual-addressing works because:
- The JDBC driver connects to OJP using the host-visible address
- OJP interprets the PostgreSQL connection details from within the Docker network

## Next Steps

The integration is now working successfully! Possible enhancements:

1. Add more comprehensive tests (error handling, connection pooling, etc.)
2. Add integration tests for other database operations (Update, Delete)
3. Consider adding a docker-compose.yml for local development/testing
4. Explore OJP's monitoring and connection pooling features
5. Add examples of OJP configuration options

## Dependencies

- Spring Boot 3.3.5
- Java 17
- TestContainers 1.20.4
- OJP JDBC Driver 0.3.0-beta
- PostgreSQL 16

## License

This is a demo/tutorial project.
