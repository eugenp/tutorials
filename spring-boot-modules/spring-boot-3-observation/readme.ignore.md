https://github.com/spring-projects/spring-framework/wiki/What%27s-New-in-Spring-Framework-6.x

Direct Observability instrumentation with [Micrometer Observation](https://micrometer.io/docs/observation)
in several parts of the Spring Framework. The `spring-web` module now requires 
`io.micrometer:micrometer-observation:1.10+` as a compile dependency.

- RestTemplate and WebClient are instrumented to produce HTTP client request observations.
- Spring MVC can be instrumented for HTTP server observations using the new 
  `org.springframework.web.filter.ServerHttpObservationFilter`.
- Spring WebFlux can be instrumented for HTTP server observations using the new 
  `org.springframework.web.filter.reactive.ServerHttpObservationFilter`.
- Integration with Micrometer [Context Propagation](https://github.com/micrometer-metrics/context-propagation#context-propagation-library)
  for Flux and Mono return values from controller methods.

- ObservationRegistry autoconfigured in Actuator - we can declare a simple bean by ourselfes
  - automatic instr.
  - configuration properties
- test without actuator - what we get when using actuator
- using Jaeger etc. (https://www.baeldung.com/distributed-systems-observability)
