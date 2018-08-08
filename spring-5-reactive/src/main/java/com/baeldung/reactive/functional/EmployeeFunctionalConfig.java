package com.baeldung.reactive.functional;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
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

import reactor.core.publisher.Mono;

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
    RouterFunction<ServerResponse> updatedEmployee() {
        return route(POST("/employees/update"), req -> {               
            Employee employee = req.body(toMono(Employee.class)).block();
            Mono<Employee> updated=employeeRepository().updateEmployee(employee);
            return ok().body(updated, Employee.class);
        });
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf()
            .disable()
            .authorizeExchange()
            .anyExchange()
            .permitAll();
        return http.build();
    }
}
