package com.baeldung.passexceptiontoclientjsonspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() throws CustomException {

        if (true) {
            throw new CustomException();
        }

        return "index";

    }

}