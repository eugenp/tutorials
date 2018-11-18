package com.baeldung.swaggerboot.controller;

import com.baeldung.swaggerboot.services.RegularWebService;
import com.baeldung.swaggerboot.transfer.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.baeldung.swaggerboot.Constants.REGULAR_REST_URL;

@RestController
public class RegularRestController {

    @Autowired
    RegularWebService regularWebService;

    @GetMapping(REGULAR_REST_URL)
    public CustomResponse getSession() {
        return regularWebService.example();
    }

}