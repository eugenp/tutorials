package com.cloud.springwebsite.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CloudSiteController {
    
    @Autowired
    private RestOperations restOperations;

    
    @Value("${person.url}")
    private String personUrl;
    
     
    @RequestMapping("/")
    @ResponseBody
    public String helloFromBaeldung() {
        return "Hello From Baeldung!";
    }

    
    @RequestMapping("/person")
    public ModelAndView person(){
        ModelAndView mav = new ModelAndView("personinfo");
        mav.addObject("person",restOperations.getForObject(personUrl, String.class));        
        return mav;    
    }
      
}