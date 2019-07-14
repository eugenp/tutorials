package com.baeldung.hexagonal.restadapters;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.domain.ports.GetUserService;
import java.util.List;


@RestController
public class UsersRestController {

    private GetUserService getUserService;

    @Autowired
    public UsersRestController(GetUserService getUserService) {
        this.getUserService = getUserService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return getUserService.getAllUsers();
    }
}
