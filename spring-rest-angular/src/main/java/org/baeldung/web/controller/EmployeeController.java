package org.baeldung.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmployeeController {

    @RequestMapping(value = "/employeePage")
    public String getEmployeePage() {
        return "employee";
    }

}
