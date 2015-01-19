package org.baeldung.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.baeldung.persistence.model.User;
import org.baeldung.persistence.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    public UserController() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public List<User> findAll(@RequestParam(value = "search", required = false) final String search) {
        final Map<String, Object> params = new HashMap<String, Object>();

        if (search != null) {
            final Pattern pattern = Pattern.compile("(\\w+?):(\\w+?),");
            final Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.put(matcher.group(1), matcher.group(2));
            }
        }
        return service.searchUser(params);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/spec")
    @ResponseBody
    public List<User> findAllBySpecification(@RequestParam(value = "search", required = false) final String search) {
        final Map<String, Object> params = new HashMap<String, Object>();

        if (search != null) {
            final Pattern pattern = Pattern.compile("(\\w+?):(\\w+?),");
            final Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.put(matcher.group(1), matcher.group(2));
            }
        }
        return service.searchBySpecification(params);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/new")
    @ResponseBody
    public long addUser(@RequestParam("first") final String first, @RequestParam("last") final String last, @RequestParam("age") final int age) {
        final User user = new User();
        user.setFirstName(first);
        user.setLastName(last);
        user.setEmail("john@doe.com");
        user.setAge(age);
        service.saveUser(user);
        return user.getId();
    }
}
