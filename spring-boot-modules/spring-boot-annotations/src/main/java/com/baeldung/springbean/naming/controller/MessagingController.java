package com.baeldung.springbean.naming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.baeldung.springbean.naming.service.MessagingService;

@Controller
public class MessagingController {

    @Autowired
    private MessagingService service;
}
