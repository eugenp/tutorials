package com.baeldung.ctx2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.parent.IHomeService;

@RestController
public class Ctx2Controller {

    @Autowired
    IHomeService homeService;

    @GetMapping(value = "/greeting", produces = "application/json")
    public String getGreeting() {
        return homeService.getGreeting();
    }
}
