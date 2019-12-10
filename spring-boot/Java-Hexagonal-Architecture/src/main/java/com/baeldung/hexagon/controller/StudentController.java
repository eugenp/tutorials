/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.hexagon.controller;

import com.baeldung.hexagon.service.StudentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author NandomPC
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping(value = "/student")
    public List getAllPets() {
        return service.getAllStudents();
    }

}
