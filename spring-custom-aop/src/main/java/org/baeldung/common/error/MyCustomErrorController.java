package org.baeldung.common.error;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class MyCustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    public MyCustomErrorController() {
        // TODO Auto-generated constructor stub
    }

    @RequestMapping(value = PATH)
    public String error() {
        return "Error heaven";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

}
