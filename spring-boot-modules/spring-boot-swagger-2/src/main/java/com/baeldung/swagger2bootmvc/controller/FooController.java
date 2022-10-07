package com.baeldung.swagger2bootmvc.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.baeldung.swagger2bootmvc.model.Foo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Controller
public class FooController {

    public FooController() {
        super();
    }

    // API - write
    @RequestMapping(method = RequestMethod.POST, value = "/foos")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "foo", value = "List of strings", paramType = "body", dataType = "Foo") })
    public Foo create(@RequestBody final Foo foo) {
        foo.setId(Long.parseLong(randomNumeric(2)));
        return foo;
    }

}
