package com.baeldung.jvmargs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MyController {

    private final MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/length")
    Integer getLength() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        return myService.getLength();
    }

}