package org.baeldung.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.baeldung.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "/employee/{Id}", method = RequestMethod.GET)
    public String getEmployeeByIdHtmlView(@PathVariable final long Id, final ModelMap model) {
        model.addAttribute("name", employeeMap.get(Id).getName());
        model.addAttribute("contactNumber", employeeMap.get(Id).getContactNumber());
        model.addAttribute("id", employeeMap.get(Id).getId());

        return "employeeView";
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String submit(@ModelAttribute("employee") final Employee employee, final BindingResult result, final ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("name", employee.getName());
        model.addAttribute("contactNumber", employee.getContactNumber());
        model.addAttribute("id", employee.getId());

        employeeMap.put(employee.getId(), employee);

        return "employeeView";
    }

}
