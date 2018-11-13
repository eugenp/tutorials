HEXAGONAL ARCHITECTURE STARTER PROJECT
================================

This is the code of a simple example for hexagonal architecure.

### Requirements

- Maven
- JDK 8

### Running

To build and start the server simply type

```bash
$ mvn clean install
$ mvn spring-boot:run
```

### Available Endpoint

```bash
$ curl localhost:8080/api/v1/devices/device/smarttv


With port and adapter
```bash
$ curl localhost:8080/api/v2/devices/device/smarttv
```