package com.baeldung.springbootmvc.slash;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("slash")
public class SlashParsingController {

    @GetMapping("pathParam/{rest}")
    public String pathParam(HttpServletRequest request, @PathVariable("rest") String rest) {
        return rest;
    }

    @GetMapping("all/**")
    public String allDirectories(HttpServletRequest request) {
        return request.getRequestURI()
            .split(request.getContextPath() + "/all/")[1];
    }

    @GetMapping("param")
    public String parameter(@RequestParam("p") String param) {
        return param;
    }

}
