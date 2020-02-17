package org.baeldung.ssl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcome() {
        return "ssl/welcome";
    }

}
