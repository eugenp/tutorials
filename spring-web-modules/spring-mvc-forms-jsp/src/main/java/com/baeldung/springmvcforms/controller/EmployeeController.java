package com.baeldung.springmvcforms.controller;

import com.baeldung.springmvcforms.domain.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EmployeeController {

    Map<Long, Employee> employeeMap = new HashMap<>();

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("employeeHome", "employee", new Employee());
    }

    @RequestMapping(value = "/employee/{Id}", produces = { "application/json", "application/xml" }, method = RequestMethod.GET)
    public @ResponseBody Employee getEmployeeById(@PathVariable final long Id) {
        return employeeMap.get(Id);
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST, params = "submit")
    public String submit(@Valid @ModelAttribute("employee") final Employee employee, final BindingResult result, final ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("name", employee.getName());
        model.addAttribute("contactNumber", employee.getContactNumber());
        model.addAttribute("id", employee.getId());
        employeeMap.put(employee.getId(), employee);
        return "employeeView";
    }
    
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST, params = "cancel")
    public String cancel(@Valid @ModelAttribute("employee") final Employee employee, final BindingResult result, final ModelMap model) {
        model.addAttribute("message", "You clicked cancel, please re-enter employee details:");
        return "employeeHome";
    }

}
