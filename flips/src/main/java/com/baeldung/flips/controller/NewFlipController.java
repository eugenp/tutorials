package com.baeldung.flips.controller;

import com.baeldung.flips.model.Thing;
import com.baeldung.flips.service.FlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewFlipController {

    private FlipService flipService;

    @Autowired
    public NewFlipController(FlipService flipService) {
        this.flipService = flipService;
    }

    @RequestMapping(value = "/alt/thing/new", method = RequestMethod.GET)
    public Thing getNewThing(){
        return flipService.getNewThing();
    }
}