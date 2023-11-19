## CAS

This module contains articles about the Central Authentication Service (CAS).

The module consists of 2 submodules:
1. `cas-server` - it requires JDK11 and uses the Gradle War Overlay style to ease setup and deployment. To  start the server, simply run:

`./gradlew run
  -Dorg.gradle.java.home=$JAVA11_HOME
  -Pargs="-Dcas.standalone.configurationDirectory=/cas-server/src/main/resources/etc/cas/config"`

The server starts at https://localhost:8443. `casuser`/`Mellon` are the username and password for logging in.

2. `cas-secured-app` - A Maven based Springboot Application

### Relevant Articles: 

