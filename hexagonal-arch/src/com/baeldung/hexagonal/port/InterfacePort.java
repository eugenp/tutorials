package com.baeldung.hexagonal.port;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface InterfacePort {
    
    @GetMapping("create")
    public void create(@RequestParam(name="id") Integer id, @RequestParam(name="name") String name);
    
    @GetMapping("getGrades")
    public Map<String, Integer> getGrades(@RequestParam(name="id") Integer id);
    
    @GetMapping("setGrade")
    public void setGrade(
        @RequestParam(name="id") Integer id,
        @RequestParam(name="name") String name,
        @RequestParam(name="grade") Integer grade);

}
