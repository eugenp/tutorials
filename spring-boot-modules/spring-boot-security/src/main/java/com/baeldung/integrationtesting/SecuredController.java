package com.baeldung.integrationtesting;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredController {

    @GetMapping("/public/hello")
    public List<String> publicHello() {
        return Arrays.asList("Hello", "World", "from", "Public");
    }

    @GetMapping("/private/hello")
    public List<String> privateHello() {
        return Arrays.asList("Hello", "World", "from", "Private");
    }

}
