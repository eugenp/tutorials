package com.baeldung.matrix.controller;

import com.baeldung.matrix.model.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CompanyController {

    Map<Long, Company> companyMap = new HashMap<>();

    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("companyHome", "company", new Company());
    }

    @RequestMapping(value = "/company/{Id}", produces = { "application/json", "application/xml" }, method = RequestMethod.GET)
    public @ResponseBody Company getCompanyById(@PathVariable final long Id) {
        return companyMap.get(Id);
    }

    @RequestMapping(value = "/addCompany", method = RequestMethod.POST)
    public String submit(@ModelAttribute("company") final Company company, final BindingResult result, final ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("name", company.getName());
        model.addAttribute("id", company.getId());

        companyMap.put(company.getId(), company);

        return "companyView";
    }

    @RequestMapping(value = "/companyEmployee/{company}/employeeData/{employee}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, String>> getEmployeeDataFromCompany(@MatrixVariable(pathVar = "employee") final Map<String, String> matrixVars) {
        return new ResponseEntity<>(matrixVars, HttpStatus.OK);
    }

    @RequestMapping(value = "/companyData/{company}/employeeData/{employee}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, String>> getCompanyName(@MatrixVariable(value = "name", pathVar = "company") final String name) {
        final Map<String, String> result = new HashMap<String, String>();
        result.put("name", name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
