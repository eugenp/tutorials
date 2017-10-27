package org.baeldung.xml.spring.controller;


import org.baeldung.spring.model.User;
import org.baeldung.spring.service.IGeoLocationService;
import org.baeldung.spring.service.IUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UserController {

    private IUserService userService;

    private IGeoLocationService locationService;

    public UserController(IUserService userService){
        this.userService = userService;
    }

    public void setLocationService(IGeoLocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(value = "/api/xml-config/user",method = POST)
    public String addUser(@RequestBody User user){
        System.out.println(String.format("Name :: %s",user.getName()));
        System.out.println(String.format("Address :: %s",null != user.getAddress() ? user.getAddress().getAddress() : "No Address"));
        System.out.println(String.format("Details :: %s",null != user.getDetails() ? user.getDetails().getDetails() : "No Details"));
        userService.save(user);
        locationService.saveLocation("Location");
        return String.format("%s added successfully",user.getName());
    }
}
