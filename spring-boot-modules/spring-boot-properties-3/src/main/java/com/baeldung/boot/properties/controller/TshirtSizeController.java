package com.baeldung.boot.data.controller;

import org.springframework.web.bind.annotation.*;
import com.baeldung.boot.data.service.SizeConverterService;

@RestController
@RequestMapping(value = "/")
public class TshirtSizeController {

    private final SizeConverterService service;

    public TshirtSizeController(SizeConverterService service) {
        this.service = service;
    }

    @RequestMapping(value ="convertSize", method = RequestMethod.GET)
    public int convertSize(@RequestParam(value = "label") final String label,
                                             @RequestParam(value = "countryCode", required = false) final String countryCode) {
        return service.convertSize(label, countryCode);
    }

}
