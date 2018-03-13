package com.baeldung.flips.controller;

import com.baeldung.flips.model.Thing;
import com.baeldung.flips.service.FlipService;
import org.flips.annotation.FlipBean;
import org.flips.annotation.FlipOnEnvironmentProperty;
import org.flips.annotation.FlipOnProfiles;
import org.flips.annotation.FlipOnSpringExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlipController {

    private FlipService flipService;

    @Autowired
    public FlipController(FlipService flipService) {
        this.flipService = flipService;
    }

    @RequestMapping(value = "/things", method = RequestMethod.GET)
    @FlipOnProfiles(activeProfiles = "dev")
    public List<Thing> getAllThings(){
        return flipService.getAllThings();
    }

    @RequestMapping(value = "/things/{id}", method = RequestMethod.GET)
    @FlipOnEnvironmentProperty(property = "feature.thing.by.id", expectedValue = "Y")
    public Thing getThingById(@PathVariable int id){
        return flipService.getThingById(id).orElse(new Thing("Not Found", -1));
    }

    @FlipBean(with = NewFlipController.class)
    @FlipOnSpringExpression(expression = "@environment.getProperty('feature.new.thing') == 'Y'")
    @RequestMapping(value = "/thing/new", method = RequestMethod.GET)
    public Thing getNewThing(){
        return new Thing("Not Found", -1);
    }
}