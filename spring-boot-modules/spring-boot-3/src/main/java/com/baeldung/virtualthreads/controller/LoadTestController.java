package com.baeldung.virtualthreads.controller;

import com.baeldung.virtualthreads.SynchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/load")
public class LoadTestController {

    @Autowired
    private SynchService service;

    @GetMapping
    public void doSomething() throws InterruptedException, IOException {
        service.synchMethod();
    }

}
