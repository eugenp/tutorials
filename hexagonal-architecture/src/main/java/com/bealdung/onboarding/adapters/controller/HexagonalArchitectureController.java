package com.bealdung.onboarding.adapters.controller;
import com.bealdung.onboarding.application.service.EmployeeServicePort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value = "/employee")
public class HexagonalArchitectureController {
    private EmployeeServicePort employeeService;
    public HexagonalArchitectureController(EmployeeServicePort employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping(value = "/find")
    public String findEmployee(@RequestParam String id) {
        return employeeService.getNameAndFamily(Long.parseLong(id));
    }
}
