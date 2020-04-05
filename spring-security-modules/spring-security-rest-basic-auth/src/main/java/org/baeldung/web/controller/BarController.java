package org.baeldung.web.controller;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.baeldung.web.dto.Bar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/bars")
public class BarController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public BarController() {
        super();
    }

    // API

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Bar findOne(@PathVariable("id") final Long id) {
        return new Bar();
    }

    public HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
              String auth = username + ":" + password;
              byte[] encodedAuth = Base64.encodeBase64( 
                 auth.getBytes(Charset.forName("US-ASCII")) );
              String authHeader = "Basic " + new String( encodedAuth );
              set( "Authorization", authHeader );
           }};
     }
}
