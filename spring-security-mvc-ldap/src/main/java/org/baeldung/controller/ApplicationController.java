package org.baeldung.controller;

import org.baeldung.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicationController {

@Autowired
private UserDao userDao;

    @GetMapping("/allUserNames")
    public String listAllNames() {
        StringBuilder stringBuilder = new StringBuilder("");
        List<String> allPersonNames = userDao.getAllUserNames();
        allPersonNames.stream().forEach(i-> stringBuilder.append("\""+i+"\"").append("  -  "));
        return stringBuilder.toString();
    }
}
