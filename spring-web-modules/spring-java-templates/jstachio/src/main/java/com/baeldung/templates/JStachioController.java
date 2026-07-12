package com.baeldung.templates;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;

import io.jstach.opt.spring.webmvc.JStachioModelView;

@Controller
public class JStachioController {
    @GetMapping("/jstachio")
    public View get() {
        return JStachioModelView.of(new JStachioModel("JStachio"));
    }
}
