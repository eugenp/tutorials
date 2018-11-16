package com.baeldung.spring.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class RequestParamController {

    @GetMapping("/api/foos")
    @ResponseBody
    public String getFoos(@RequestParam String id){
        return "ID: " + id;
    }
    
    @PostMapping("/api/foos")
    @ResponseBody
    public String addFoo(@RequestParam(name = "id") String fooId, @RequestParam String name){ 
        return "ID: " + fooId;
    }
    
    @GetMapping("/api/foos2")
    @ResponseBody
    public String getFoos2(@RequestParam(required = false) String id){ 
        return "ID: " + id;
    }
    
    @GetMapping("/api/foos3")
    @ResponseBody
    public String getFoos3(@RequestParam(defaultValue = "test") String id){
        return "ID: " + id;
    }
    
    @PostMapping("/api/foos1")
    @ResponseBody
    public String updateFoos(@RequestParam Map<String,String> allParams){
        return "Parameters are " + allParams.entrySet();
    }
    
    @GetMapping("/api/foos4")
    @ResponseBody
    public String getFoos4(@RequestParam List<String> id){
        return "ID are " + id;
    }
    
    @GetMapping("/foos/{id}")
    @ResponseBody
    public String getFooById(@PathVariable String id){
        return "ID: " + id;
    }
    
    @GetMapping("/foos")
    @ResponseBody
    public String getFooByIdUsingQueryParam(@RequestParam String id){
        return "ID: " + id;
    }
    
    @GetMapping({"/myfoos/optional", "/myfoos/optional/{id}"})
    @ResponseBody
    public String getFooByOptionalId(@PathVariable(required = false) String id){
        return "ID: " + id;
    }
    
    @GetMapping("/myfoos/optionalParam")
    @ResponseBody
    public String getFooByOptionalIdUsingQueryParam(@RequestParam(required = false) String id){
        return "ID: " + id;
    }
    
    
    
}
