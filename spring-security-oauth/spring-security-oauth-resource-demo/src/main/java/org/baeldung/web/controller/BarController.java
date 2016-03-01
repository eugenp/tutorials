package org.baeldung.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import org.baeldung.web.dto.Bar;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class BarController {

    public BarController() {
        super();
    }

    // API - read
    // @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.GET, value = "/bars/{id}")
    @ResponseBody
    public Bar findById(@PathVariable final long id) {
        return new Bar(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
    }

    // API - write
    // @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, value = "/bars")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Bar create(@RequestBody final Bar bar) {
        bar.setId(Long.parseLong(randomNumeric(2)));
        return bar;
    }

}
