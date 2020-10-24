package com.baeldung.filterandinterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private static Map<String, Employee> employeeMap = new HashMap<String, Employee>() {
        private static final long serialVersionUID = 1L;

        {
            put("1001", new Employee("1001", "User1", "Manager", 20000.0));
            put("1002", new Employee("1002", "User2", "Software Engineer", 10000.0));
        }
    };

    @RequestMapping("/isEmployee")
    public boolean checkIfExist(@RequestParam String id) {
        Employee employee = employeeMap.get(id);
        if (employee != null) {
            return true;
        }
        return false;
    }

    @RequestMapping("/details")
    public Employee getEmployee(@RequestParam String id) {
        logger.info("Employee Id is : " + id);
        return employeeMap.get(id);
    }

}
