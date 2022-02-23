package com.baeldung.matrix.controller;


import com.baeldung.matrix.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@SessionAttributes("employees")
@Controller
public class EmployeeController {

    public Map<Long, Employee> employeeMap = new HashMap<>();

    @ModelAttribute("employees")
    public void initEmployees() {
        employeeMap.put(1L, new Employee(1L, "John", "223334411", "rh"));
        employeeMap.put(2L, new Employee(2L, "Peter", "22001543", "informatics"));
        employeeMap.put(3L, new Employee(3L, "Mike", "223334411", "admin"));
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("employeeHome", "employee", new Employee());
    }

    @RequestMapping(value = "/employee/{Id}", produces = { "application/json", "application/xml" }, method = RequestMethod.GET)
    public @ResponseBody Employee getEmployeeById(@PathVariable final long Id) {
        return employeeMap.get(Id);
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String submit(@ModelAttribute("employee") final Employee employee, final BindingResult result, final ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("name", employee.getName());
        model.addAttribute("contactNumber", employee.getContactNumber());
        model.addAttribute("workingArea", employee.getWorkingArea());
        model.addAttribute("id", employee.getId());

        employeeMap.put(employee.getId(), employee);

        return "employeeView";
    }

    @RequestMapping(value = "/employees/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Employee>> getEmployeeByNameAndBeginContactNumber(@PathVariable final String name, @MatrixVariable final String beginContactNumber) {
        final List<Employee> employeesList = new ArrayList<Employee>();
        for (final Map.Entry<Long, Employee> employeeEntry : employeeMap.entrySet()) {
            final Employee employee = employeeEntry.getValue();
            if (employee.getName().equalsIgnoreCase(name) && employee.getContactNumber().startsWith(beginContactNumber)) {
                employeesList.add(employee);
            }
        }
        return new ResponseEntity<>(employeesList, HttpStatus.OK);
    }

    @RequestMapping(value = "/employeesContacts/{contactNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Employee>> getEmployeeByContactNumber(@MatrixVariable(required = true) final String contactNumber) {
        final List<Employee> employeesList = new ArrayList<Employee>();
        for (final Map.Entry<Long, Employee> employeeEntry : employeeMap.entrySet()) {
            final Employee employee = employeeEntry.getValue();
            if (employee.getContactNumber().equalsIgnoreCase(contactNumber)) {
                employeesList.add(employee);
            }
        }
        return new ResponseEntity<>(employeesList, HttpStatus.OK);
    }

    @RequestMapping(value = "employeeData/{employee}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, String>> getEmployeeData(@MatrixVariable final Map<String, String> matrixVars) {
        return new ResponseEntity<>(matrixVars, HttpStatus.OK);
    }

    @RequestMapping(value = "employeeArea/{workingArea}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Employee>> getEmployeeByWorkingArea(@MatrixVariable final Map<String, List<String>> matrixVars) {
        final List<Employee> employeesList = new ArrayList<>();
        final Collection<String> workingArea = matrixVars.get("workingArea");
        for (final Map.Entry<Long, Employee> employeeEntry : employeeMap.entrySet()) {
            final Employee employee = employeeEntry.getValue();
            for (final String area : workingArea) {
                if (employee.getWorkingArea().equalsIgnoreCase(area)) {
                    employeesList.add(employee);
                    break;
                }
            }
        }
        return new ResponseEntity<>(employeesList, HttpStatus.OK);
    }

}
