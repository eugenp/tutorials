/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mansi
 */
@RestController
public class PathPatternController {

    @RequestMapping(value = "/test/{*id}")
    public String URIVariableHandler(@PathVariable String id) {
        return id;
    }
}
