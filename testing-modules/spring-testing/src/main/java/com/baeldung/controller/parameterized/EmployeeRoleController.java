package com.baeldung.controller.parameterized;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class EmployeeRoleController {

    private static Map<String, Role> userRoleCache = new HashMap<>();

    static {
        userRoleCache.put("John", Role.ADMIN);
        userRoleCache.put("Doe", Role.EMPLOYEE);
    }

    @RequestMapping(value = "/role/{name}", method = RequestMethod.GET, produces = "application/text")
    @ResponseBody
    public String getEmployeeRole(@PathVariable("name") String employeeName) {

        return userRoleCache.get(employeeName).toString();
    }

    private enum Role {
        ADMIN, EMPLOYEE
    }
}
