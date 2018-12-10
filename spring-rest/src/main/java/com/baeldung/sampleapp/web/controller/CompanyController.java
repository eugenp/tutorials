package com.baeldung.sampleapp.web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.sampleapp.web.dto.Company;

@RestController
public class CompanyController {

    @RequestMapping(value = "/companyRest", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company getCompanyRest() {
        final Company company = new Company(1, "Xpto");
        return company;
    }
}
