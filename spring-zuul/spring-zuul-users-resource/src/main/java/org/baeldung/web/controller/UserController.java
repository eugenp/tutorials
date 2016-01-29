package org.baeldung.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.baeldung.web.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    public UserController() {
        super();
    }

    // API - read
    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    @ResponseBody
    public User findById(@PathVariable final long id, HttpServletRequest req, HttpServletResponse res) {
        return new User(Long.parseLong(randomNumeric(2)), randomAlphabetic(4), Integer.parseInt(randomNumeric(2)));
    }

}
