package com.baeldung.reactive.webflux;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/{id}")
    private Mono<Employee> getEmployeeById(@PathVariable String id) {
        return employeeRepository.findEmployeeById(id);
    }

    @GetMapping
    private Flux<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

    @PostMapping("/update")
    private Mono<Employee> updateEmployee(@RequestBody Employee employee) {
        return employeeRepository.updateEmployee(employee);
    }

    @GetMapping(value = "/{id}/track", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<EmployeeEvent> trackEmployee(@PathVariable String id) {
        return employeeRepository.findEmployeeById(id)
            .flatMapMany(employee -> {

                Flux<EmployeeEvent> employeeEvents = Flux.fromStream(Stream.generate(() -> new EmployeeEvent(employee, new Date())));

                Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

                return Flux.zip(interval, employeeEvents)
                    .map(objects -> objects.getT2());

            });
    }

}
