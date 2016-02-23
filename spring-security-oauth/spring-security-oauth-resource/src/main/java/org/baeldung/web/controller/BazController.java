package org.baeldung.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import org.baeldung.web.dto.Baz;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class BazController {

    public BazController() {
        super();
    }

    // API - read
    // @PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, value = "/bazes/{id}")
    @ResponseBody
    public Baz findById(@PathVariable final long id) {
        return new Baz(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
    }

    // API - write
    // @PreAuthorize("#oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, value = "/bazes")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Baz create(@RequestBody final Baz baz) {
        baz.setId(Long.parseLong(randomNumeric(2)));
        return baz;
    }

}
