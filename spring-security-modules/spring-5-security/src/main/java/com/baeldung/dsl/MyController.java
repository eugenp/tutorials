package com.baeldung.dsl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/admin")
    public String getAdminPage() {
        return "Hello Admin";
    }

}
