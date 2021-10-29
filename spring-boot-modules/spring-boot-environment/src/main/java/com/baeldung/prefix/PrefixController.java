package com.baeldung.prefix;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrefixController {

    @Value(value = "${server.port}")
    private int serverPort;

    @GetMapping("/prefix")
    public String getServerPortInfo(final Model model) {
        model.addAttribute("serverPort", serverPort);
        return "prefix";
    }
}
