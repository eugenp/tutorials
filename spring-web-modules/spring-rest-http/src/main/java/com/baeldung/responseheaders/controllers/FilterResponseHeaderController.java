package com.baeldung.responseheaders.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/filter-response-header")
public class FilterResponseHeaderController {

    @GetMapping("/no-extra-header")
    public String filterHeaderResponseWithNoExtraHeader() {
        return "Response body with Filter header and no extra header";
    }

    @GetMapping("/extra-header")
    public String filterHeaderResponseWithExtraHeader(HttpServletResponse response) {
        response.addHeader("Baeldung-Example-Header", "Value-ExtraHeader");
        return "Response body with Filter header and one extra header";
    }

}
