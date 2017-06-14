package com.baeldung.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathPatternController {

    @RequestMapping(value = "/spring5/{*id}")
    public String URIVariableHandler(@PathVariable String id) {
        return id;
    }
    
    @RequestMapping(value = "/s?ring5")
    public String wildcardTakingExactlyOneChar() {
        return "/s?ring5";
    }
    
    @RequestMapping(value = "/spring5/*id")
    public String wildcardTakingZeroOrMoreChar() {
        return "/spring5/*id";
    }
    
    @RequestMapping(value = "/resources/**")
    public String wildcardTakingZeroOrMorePathSegments() {
        return "/resources/**";
    }
    
    @RequestMapping(value = "/{baeldung:[a-z]+}")
    public String regexInPathVariable(@PathVariable String baeldung) {
        return baeldung;
    }
    
    @RequestMapping(value = "/{var1}_{var2}")
    public String multiplePathVariablesInSameSegment(@PathVariable String var1,@PathVariable String var2) {
        return "Two variables are var1="+var1+" and var2="+var2;
    }
}
