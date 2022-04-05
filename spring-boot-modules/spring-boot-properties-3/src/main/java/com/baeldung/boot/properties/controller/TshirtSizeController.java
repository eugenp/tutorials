package com.baeldung.boot.properties.controller;

import org.springframework.web.bind.annotation.*;
import com.baeldung.boot.properties.service.SizeConverterService;

@RestController
@RequestMapping(value = "/")
public class TshirtSizeController {

    private final SizeConverterService service;

    public TshirtSizeController(SizeConverterService service) {
        this.service = service;
    }

    @RequestMapping(value ="convertSize", method = RequestMethod.GET)
    public int convertSize(@RequestParam(value = "label") final String label, @RequestParam(value = "countryCode", required = false) final String countryCode) {
        return service.convertSize(label, countryCode);
    }

}
