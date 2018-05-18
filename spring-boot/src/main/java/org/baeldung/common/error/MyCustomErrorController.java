package org.baeldung.common.error;

import org.springframework.boot.web.servlet.error.ErrorController;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMapping;
=======
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> upstream/master

public class MyCustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    public MyCustomErrorController() {
        // TODO Auto-generated constructor stub
    }

    @GetMapping(value = PATH)
    public String error() {
        return "Error heaven";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
