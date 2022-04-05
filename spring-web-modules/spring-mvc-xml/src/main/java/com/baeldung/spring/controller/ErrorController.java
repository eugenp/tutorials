package com.baeldung.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping(value = "500Error", method = RequestMethod.GET)
    public void throwRuntimeException() {
        throw new NullPointerException("Throwing a null pointer exception");
    }

    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
        case 400: {
            errorMsg = "Http Error Code : 400 . Bad Request";
            break;
        }
        case 401: {
            errorMsg = "Http Error Code : 401. Unauthorized";
            break;
        }
        case 404: {
            errorMsg = "Http Error Code : 404. Resource not found";
            break;
        }
        // Handle other 4xx error codes.
        case 500: {
            errorMsg = "Http Error Code : 500. Internal Server Error";
            break;
        }
        // Handle other 5xx error codes.
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
}
