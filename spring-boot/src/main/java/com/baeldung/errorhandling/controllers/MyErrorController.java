package com.baeldung.errorhandling.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    public MyErrorController() {}

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request) {

        Integer statusCode =
          Integer.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());

        if(statusCode == HttpStatus.NOT_FOUND.value()) {
            return "error-404";
        }
        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return "error-500";
        }
        else {
            return "error";
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
