package com.baeldung.reactive.functional;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.reactive.webflux.Employee;
import com.baeldung.reactive.webflux.EmployeeRepository;

@Configuration
public class EmployeeFunctionalConfig {

    @Bean
    EmployeeRepository employeeRepository() {
        return new EmployeeRepository();
    }

    @Bean
    RouterFunction<ServerResponse> getAllEmployeesRoute() {
        return route(path("/employees"), 
            req -> ok().body(employeeRepository().findAllEmployees(), Employee.class));
    }

    @Bean
    RouterFunction<ServerResponse> getEmployeeByIdRoute() {
        return route(path("/employee/{id}"), 
            req -> ok().body(employeeRepository().findEmployeeById(req.pathVariable("id")), Employee.class));
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
            .anyExchange()
            .permitAll();
        return http.build();
    }
}
