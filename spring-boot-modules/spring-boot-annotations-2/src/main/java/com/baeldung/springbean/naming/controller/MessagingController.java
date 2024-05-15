package com.baeldung.springbean.naming.controller;

import com.baeldung.springbean.naming.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingController {

    @Autowired
    private MessagingService service;
}
