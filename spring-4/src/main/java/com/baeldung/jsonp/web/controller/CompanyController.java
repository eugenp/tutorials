package com.baeldung.jsonp.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.jsonp.model.Company;

@Controller
public class CompanyController {

    @RequestMapping(value = "/companyResponseBody", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Company getCompanyResponseBody() {
        final Company company = new Company(2, "ABC");
        return company;
    }

    @RequestMapping(value = "/companyResponseEntity", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> getCompanyResponseEntity() {
        final Company company = new Company(3, "123");
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }
}
