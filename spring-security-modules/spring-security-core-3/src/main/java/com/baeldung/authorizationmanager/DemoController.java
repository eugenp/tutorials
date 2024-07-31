package com.baeldung.authorizationmanager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/anonymous")
    public String anonymousResource() {
        return "anonymous";
    }

    @GetMapping("/adminonly")
    public String adminResource() {
        return "admin only";
    }

    @GetMapping("/authororeditor")
    public String authorOrEditorResource() {
        return "author or editor";
    }

    @GetMapping("/custom")
    public String customResource() {
        return "custom";
    }
}

