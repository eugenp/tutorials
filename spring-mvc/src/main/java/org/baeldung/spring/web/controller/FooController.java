package org.baeldung.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FooController {

    public FooController() {
        super();
    }

    // API

    // by paths

    @RequestMapping(value = "/foos")
    @ResponseBody
    public String getFoosBySimplePath() {
        return "Simple Get some Foos";
    }

    // @RequestMapping(value = "/foos")
    // @ResponseBody
    // public String getFoosByAdvancedPath() {
    // return "Advanced Get some Foos";
    // }

    // with @PathVariable

    @RequestMapping(value = "/foos/{id}")
    @ResponseBody
    public String getFoosBySimplePathWithPathVariable(@PathVariable("id") final long id) {
        return "Get a specific Foo with id=" + id;
    }

    // other HTTP verbs

    @RequestMapping(value = "/foos", method = RequestMethod.POST)
    @ResponseBody
    public String postFoos() {
        return "Post some Foos";
    }

    // with headers

    @RequestMapping(value = "/foos", headers = "key=val")
    @ResponseBody
    public String getFoosWithHeader() {
        return "Get some Foos with Header";
    }

    @RequestMapping(value = "/foos", headers = { "key1=val1", "key2=val2" })
    @ResponseBody
    public String getFoosWithHeaders() {
        return "Get some Foos with Header";
    }

    // advanced - multiple mappings

    @RequestMapping(value = { "/advanced/bars", "/advanced/foos" })
    @ResponseBody
    public String getFoosOrBarsByPath() {
        return "Advanced - Get some Foos or Bars";
    }

    @RequestMapping(value = "*")
    @ResponseBody
    public String getFallback() {
        return "Fallback for GET Requests";
    }

    @RequestMapping(value = "*", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public String allFallback() {
        return "Fallback for All Requests";
    }

}
