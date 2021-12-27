package com.baeldung.prefix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrefixController {

    @Autowired
    private Environment environment;

    @GetMapping("/prefix")
    public String getServerPortInfo(final Model model) {
        model.addAttribute("serverPort", environment.getProperty("server.port"));
        return "prefix";
    }
}
