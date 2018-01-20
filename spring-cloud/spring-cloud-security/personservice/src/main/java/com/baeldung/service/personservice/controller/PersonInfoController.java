package com.baeldung.service.personservice.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.service.model.Person;

@RestController
public class PersonInfoController {
    
    @RequestMapping(value = "/currenttime")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String currentTime(){
        return LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
    
    
    @RequestMapping(value = "/person")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Person personInfo(){        
          Person   person = new Person("abir","Dhaka", "Bangladesh",29,"Male");  
        return person;        
    }    
}