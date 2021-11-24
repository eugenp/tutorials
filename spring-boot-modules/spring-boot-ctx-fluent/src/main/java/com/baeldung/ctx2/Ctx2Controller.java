package com.baeldung.ctx2;

import com.baeldung.parent.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ctx2Controller {

    @Autowired
    private IHomeService homeService;

    @GetMapping("/greeting")
    public String getGreeting() {
        return homeService.getGreeting();
    }
}
