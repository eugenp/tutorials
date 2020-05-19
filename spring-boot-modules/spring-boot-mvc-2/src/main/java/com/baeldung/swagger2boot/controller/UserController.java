package com.baeldung.swagger2boot.controller;

import com.baeldung.swagger2boot.model.Foo;
import com.baeldung.swagger2boot.model.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Controller
public class UserController {

    public UserController() {
        super();
    } //@formatter:off

    @RequestMapping(method = RequestMethod.POST, value = "/createUser", produces = "application/json; charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation(value = "Create user",
            notes = "This method creates a new user")
    public User createUser(@ApiParam(
                    name =  "firstName",
                    type = "String",
                    value = "First Name of the user",
                    example = "Vatsal",
                    required = true) @RequestParam String firstName) { //@formatter:on
        User user = new User(firstName);
        return user;
    }
}
