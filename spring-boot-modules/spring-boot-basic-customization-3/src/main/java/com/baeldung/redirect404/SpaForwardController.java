package com.baeldung.redirect404;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaForwardController {

    @RequestMapping(value = {
        "/{path:[^\\.]*}",
        "/{path:[^\\.]*}/**/{subpath:[^\\.]*}"
    })
    public String redirect() {
        return "forward:/";
    }
}
