package com.baeldung;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    @RequestMapping(method = { RequestMethod.GET }, value = { "/version" })
    public String getVersion() {
        return "1.0";
    }
}
