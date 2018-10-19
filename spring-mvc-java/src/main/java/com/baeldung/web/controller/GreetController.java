package com.baeldung.web.controller;

import com.baeldung.model.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GreetController {

    @RequestMapping(value = "/homePage", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/greet", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Greeting greet() {
        Greeting greeting = new Greeting();
        greeting.setId(1);
        greeting.setMessage("Hello World!!!");
        return greeting;
    }

    @RequestMapping(value = "/greetWithPathVariable/{name}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Greeting greetWithPathVariable(@PathVariable("name") String name) {
        Greeting greeting = new Greeting();
        greeting.setId(1);
        greeting.setMessage("Hello World " + name + "!!!");
        return greeting;
    }

    @RequestMapping(value = "/greetWithQueryVariable", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Greeting greetWithQueryVariable(@RequestParam("name") String name) {
        Greeting greeting = new Greeting();
        greeting.setId(1);
        greeting.setMessage("Hello World " + name + "!!!");
        return greeting;
    }

    @RequestMapping(value = "/greetWithPost", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Greeting greetWithPost() {
        Greeting greeting = new Greeting();
        greeting.setId(1);
        greeting.setMessage("Hello World!!!");
        return greeting;
    }

    @RequestMapping(value = "/greetWithPostAndFormData", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Greeting greetWithPostAndFormData(@RequestParam("id") int id, @RequestParam("name") String name) {
        Greeting greeting = new Greeting();
        greeting.setId(id);
        greeting.setMessage("Hello World " + name + "!!!");
        return greeting;
    }
}