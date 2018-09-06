package com.baeldung.webflux;

import java.lang.Integer;
import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class EmployeeService {

    public Flux<Employee> getEmployeeOfTheSecond() {
        Flux<Employee> employees = Flux.fromStream(
            Stream.generate(() -> 
                generateRandomEmployee()
            )
        );
        return employees.delayElements(Duration.ofSeconds(1));
    }

    private Employee generateRandomEmployee() {
        int id = new Random().nextInt(100);
        return new Employee(Integer.toString(id), "<Your name goes here>");
    }
}
