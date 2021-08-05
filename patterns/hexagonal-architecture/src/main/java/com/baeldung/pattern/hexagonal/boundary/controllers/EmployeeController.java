package com.baeldung.pattern.hexagonal.boundary.controllers;

import com.baeldung.pattern.hexagonal.boundary.mappers.EmployeeDtoMapper;
import com.baeldung.pattern.hexagonal.boundary.models.EmployeeDto;
import com.baeldung.pattern.hexagonal.domain.models.Employee;
import com.baeldung.pattern.hexagonal.domain.ports.EmployeeService;
import com.baeldung.pattern.hexagonal.domain.ports.NotFoundException;
import com.baeldung.pattern.hexagonal.domain.ports.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    // using the domains port
    private final EmployeeService employeeService;
    private final EmployeeDtoMapper employeeMapper;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> addEmployee(@Valid @RequestBody EmployeeDto dto) throws ServiceException {
        Employee incoming = employeeMapper.map(dto);
        Employee created = employeeService.addEmployee(incoming);
        EmployeeDto outgoing = employeeMapper.map(created);

        final URI location = linkTo(methodOn(EmployeeController.class).getEmployee(outgoing.getId())).toUri(); // HATEOAS
        return ResponseEntity.created(location).body(outgoing);
    }

    @GetMapping(path = "/{employeeId}")
    @SneakyThrows(NotFoundException.class)
    public EmployeeDto getEmployee(@PathVariable("employeeId") long employeeId) throws ServiceException {
        return employeeMapper.map(employeeService.getEmployee(employeeId));
    }
}
