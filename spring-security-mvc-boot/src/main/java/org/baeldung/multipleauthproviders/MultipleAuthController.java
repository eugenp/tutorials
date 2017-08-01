package org.baeldung.multipleauthproviders;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultipleAuthController {

    @RequestMapping("/api/ping")
    public String getPing() {
        return "OK";
    }
}