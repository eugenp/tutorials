package com.baeldung.solon.web;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Param;

@Controller
public class DemoController {

    @Get
    @Mapping("/hello")
    public String hello(@Param(defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }
}
