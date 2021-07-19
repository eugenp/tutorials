package com.baeldung.cachetest.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.cachetest.service.NumberService;

@RestController
@RequestMapping(path = "/number", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NumberController {

    private final static Logger log = LoggerFactory.getLogger(NumberController.class);

    @Autowired
    private NumberService numberService;

    @GetMapping(path = "/square/{number}")
    public String getThing(@PathVariable Long number) {
        log.info("call numberService to square {}", number);
        return String.format("{\"square\": %s}", numberService.square(number));
    }

}
