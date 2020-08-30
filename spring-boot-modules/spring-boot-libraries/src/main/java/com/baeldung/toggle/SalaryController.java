package com.baeldung.toggle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SalaryController {

    @Autowired
    SalaryService salaryService;

    @PostMapping(value = "/increaseSalary")
    @ResponseBody
    public void increaseSalary(@RequestParam long id) {
        salaryService.increaseSalary(id);
    }
}
