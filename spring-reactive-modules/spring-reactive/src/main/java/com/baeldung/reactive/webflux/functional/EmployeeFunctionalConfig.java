package com.baeldung.reactive.webflux.functional;

import com.baeldung.reactive.webflux.Employee;
import com.baeldung.reactive.webflux.EmployeeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class EmployeeFunctionalConfig {

    @Bean
    EmployeeRepository employeeRepository() {
        return new EmployeeRepository();
    }

    @Bean
    RouterFunction<ServerResponse> getAllEmployeesRoute() {
        return route(GET("/employees"), req -> ok().body(employeeRepository().findAllEmployees(), Employee.class));
    }

    @Bean
    RouterFunction<ServerResponse> getEmployeeByIdRoute() {
        return route(GET("/employees/{id}"), req -> ok().body(employeeRepository().findEmployeeById(req.pathVariable("id")), Employee.class));
    }

    @Bean
    RouterFunction<ServerResponse> updateEmployeeRoute() {
        return route(POST("/employees/update"), req -> req.body(toMono(Employee.class))
            .doOnNext(employeeRepository()::updateEmployee)
            .then(ok().build()));
    }

    @Bean
    RouterFunction<ServerResponse> composedRoutes() {
        return route(GET("/employees"), req -> ok().body(employeeRepository().findAllEmployees(), Employee.class))

            .and(route(GET("/employees/{id}"), req -> ok().body(employeeRepository().findEmployeeById(req.pathVariable("id")), Employee.class)))

            .and(route(POST("/employees/update"), req -> req.body(toMono(Employee.class))
                .doOnNext(employeeRepository()::updateEmployee)
                .then(ok().build())));
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(csrf -> csrf.disable())
            .authorizeExchange(
                exchanges -> exchanges.anyExchange().permitAll()
            );
        return http.build();
    }
}