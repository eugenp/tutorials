package com.baeldung.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class PropertyController {

    @Value("${name}")
    private String name;

    @Value("${name}")
    private static String NAME_NULL;

    private static String NAME_STATIC;

    @Value("${name}")
    public void setNameStatic(String name){
        PropertyController.NAME_STATIC = name;
    }

    @GetMapping("/properties")
    public List<String> getProperties(){
        return Arrays.asList(this.name, NAME_STATIC, NAME_NULL)  ;
    }
}
