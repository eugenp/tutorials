package com.baeldung.springdoc.demo.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.baeldung.springdoc.demo.model.Bar;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Controller
public class BarController {

    public BarController() {
        super();
    }

    // API - write
    @RequestMapping(method = RequestMethod.POST, value = "/bars")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "bar", value = "List of strings", paramType = "body", dataType = "Bar") })
    public Bar create(@RequestBody final Bar bar) {
        bar.setId(Long.parseLong(randomNumeric(2)));
        return bar;
    }

}
