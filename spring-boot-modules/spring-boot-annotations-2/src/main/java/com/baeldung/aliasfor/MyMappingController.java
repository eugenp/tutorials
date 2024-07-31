package com.baeldung.aliasfor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyMappingController {

    @MyMapping(action = RequestMethod.PATCH, route = "/test")
    public void mappingMethod() {
    }
    
}
