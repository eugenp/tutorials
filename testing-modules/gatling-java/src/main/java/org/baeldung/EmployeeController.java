package org.baeldung;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<Employee> getAllEmployees() {
        return createEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeWithId(@PathVariable("id") Long id) {
        log.info("Getting employee with ID '{}'", id);

        List<Employee> allEmployees = createEmployees();
        return allEmployees.get(ThreadLocalRandom.current()
          .nextInt(0, allEmployees.size()));
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Void> addEmployee(@RequestBody EmployeeCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {

        log.info("Creating new employee with employeeName: {}", request.getEmpName());

        URI location = uriComponentsBuilder.path("/api/employees/{id}")
          .buildAndExpand("99")
          .toUri();
        return ResponseEntity.created(location)
          .build();
    }

    private List<Employee> createEmployees() {

        Set<String> projects = new HashSet<String>();
        projects.add("proj1");
        projects.add("proj2");

        Employee employee1 = Employee.builder()
          .id(UUID.randomUUID()
            .toString())
          .address(Address.builder()
            .houseNo("1")
            .city("London")
            .postCode("HP17")
            .build())
          .projects(projects)
          .empName("Andy")
          .build();

        Employee employee2 = Employee.builder()
          .id(UUID.randomUUID()
            .toString())
          .address(Address.builder()
            .houseNo("2")
            .city("Cardiff")
            .postCode("CF12")
            .build())
          .projects(projects)
          .empName("Bob")
          .build();

        Employee employee3 = Employee.builder()
          .id(UUID.randomUUID()
            .toString())
          .address(Address.builder()
            .houseNo("4")
            .city("Burmingham")
            .postCode("BA4")
            .build())
          .projects(projects)
          .empName("Clive")
          .build();

        return Arrays.asList(employee1, employee2, employee3);

    }
}