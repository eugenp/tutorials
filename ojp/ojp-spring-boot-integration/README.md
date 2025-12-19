# OJP Spring Boot Integration Demo

This is a Spring Boot demo application that demonstrates integration with OJP (Open JDBC Proxy) using TestContainers.

## ✅ Module Structure: Complete
## ⚠️  Integration Tests: Blocked by logging conflict

The module follows the tutorials repository conventions with proper Maven profile integration. The integration test code is correct but cannot run due to a dependency conflict.

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
- Wraps the Docker image `rrobetti/ojp:0.3.1-beta`
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

### Module Structure
This module follows the tutorials repository conventions:
- Parent: `ojp` → inherits from → `parent-boot-3`  
- Included in: `default` and `integration` Maven profiles
- Java compatibility: 17+ (compiled with target 17, runs on 17 or higher)

### Build Commands

```bash
# Build from ojp parent directory
cd ojp
mvn clean install

# Build from root with integration profile
mvn clean install -Pintegration --pl ojp
```

### Known Issue: Logging Conflict

**Problem:** The `ojp-jdbc-driver:0.3.0-beta` dependency has `slf4j-simple` classes shaded directly into the jar. This conflicts with Logback inherited from `parent-boot-3`. Spring Boot detects both logging implementations and refuses to start.

**Error:**
```
LoggerFactory is not a Logback LoggerContext but Logback is on the classpath. 
Either remove Logback or the competing implementation 
(class org.slf4j.simple.SimpleLoggerFactory loaded from ojp-jdbc-driver-0.3.0-beta.jar)
```

**Possible Solutions:**
1. Update `ojp-jdbc-driver` to not shade slf4j-simple
2. Create a custom parent pom without logback dependencies
3. Use Spring Boot's logging exclusion mechanisms (attempted, unsuccessful so far)

The test code itself is correct and follows all repository conventions.

## Questions and Concerns

### ✅ Resolved: Module Structure and Profiles

**Initial Concern**: How to properly integrate the module into the tutorials repository structure.

**Resolution**: 
- Created parent `ojp` pom following repository patterns (inherits from `parent-boot-3`)
- Added `ojp` module to `default` and `integration` profiles in root pom.xml
- Integration test follows naming convention: `*IntegrationTest.java`
- Java 17 compatibility configured (works with JDK 17+)

### ⚠️ Active Issue: Logging Conflict

**Problem**: The `ojp-jdbc-driver:0.3.0-beta` artifact contains shaded `slf4j-simple` classes that conflict with Logback from the parent pom.

**Impact**: Integration tests cannot run due to Spring Boot detecting multiple logging implementations.

**Root Cause**: The ojp-jdbc-driver dependency includes org.slf4j.simple.SimpleLoggerFactory embedded in its jar file, which cannot be excluded via Maven dependency management.

**Attempted Workarounds**:
1. Excluding slf4j-simple (doesn't work - classes are shaded/embedded)
2. System properties to force Logback logging system
3. Excluding spring-boot-starter-logging from all starters
4. Setting logback dependencies to `provided` scope  
5. JUnit Platform configuration
6. Maven Surefire argLine configuration

None of these workarounds successfully resolved the conflict.

**Recommended Fix**: Update the `ojp-jdbc-driver` artifact to not shade slf4j dependencies, or provide a variant without shaded logging.

## Next Steps

**Priority 1 - Fix Logging Conflict:**
1. Contact OJP maintainers about removing shaded slf4j-simple from ojp-jdbc-driver
2. OR: Create custom parent pom for this module without logback
3. OR: Wait for updated ojp-jdbc-driver version

**Future Enhancements (after logging fixed):**
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
