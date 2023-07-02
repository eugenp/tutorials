package com.baeldung.pageentityresponse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organisation")
public class EmployeeController {

    private final EmployeeService organisationService;

    public EmployeeController(EmployeeService organisationService) {
        this.organisationService = organisationService;
    }

    @GetMapping("/employee")
    public ResponseEntity<Page<EmployeeDto>> getEmployeeData(Pageable pageable) {
        Page<EmployeeDto> employeeData = organisationService.getEmployeeData(pageable);
        return ResponseEntity.ok(employeeData);
    }

    @GetMapping("/data")
    public ResponseEntity<Page<EmployeeDto>> getData(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<EmployeeDto> empList = listImplementation();

        int totalSize = empList.size();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalSize);

        List<EmployeeDto> pageContent = empList.subList(startIndex, endIndex);

        Page<EmployeeDto> employeeDtos = new PageImpl<>(pageContent, PageRequest.of(page, size), totalSize);

        return ResponseEntity.ok()
          .body(employeeDtos);
    }

    private static List<EmployeeDto> listImplementation() {
        List<EmployeeDto> empList = new ArrayList<>();
        empList.add(new EmployeeDto("Jane", "Finance", 50000));
        empList.add(new EmployeeDto("Sarah", "IT", 70000));
        empList.add(new EmployeeDto("John", "IT", 90000));
        return empList;
    }

}
