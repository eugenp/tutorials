/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter2.web.controller;

import com.sishuok.chapter2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-21 下午9:35
 * <p>Version: 1.0
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public String hello(Model model) {
        userService.sayHello();
        model.addAttribute("msg", "hello world");

        return "user/hello";
    }
}
