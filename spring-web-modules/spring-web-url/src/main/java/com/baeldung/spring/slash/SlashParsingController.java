package com.baeldung.spring.slash;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("slash")
public class SlashParsingController {	

    @GetMapping("mypaths/{anything}")
    public String pathVariable(@PathVariable("anything") String anything) {
        return anything;
    }

    @GetMapping("all/**")
    public String allDirectories(HttpServletRequest request) {
        return request.getRequestURI()
            .split(request.getContextPath() + "/all/")[1];
    }

    @GetMapping("all")
    public String queryParameter(@RequestParam("param") String param) {
        return param;
    }

}
