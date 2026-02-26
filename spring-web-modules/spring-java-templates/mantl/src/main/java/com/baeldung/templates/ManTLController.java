package com.baeldung.templates;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;

@Controller
public class ManTLController {
    @GetMapping("/mantl")
    public View get() {
        return new StringView(() -> templates.mantl.ManTLDemo.render(new ManTLModel("Baeldung")));
    }
}
