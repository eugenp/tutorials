package com.baeldung.flips.controller;

import com.baeldung.flips.model.Thing;
import com.baeldung.flips.service.FlipService;
import org.flips.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
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

    @RequestMapping(value = "/thing/{id}", method = RequestMethod.GET)
    @FlipOnDaysOfWeek(daysOfWeek={DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY})
    public Thing getThingByNewId(@PathVariable int id){
        return flipService.getThingById(id).orElse(new Thing("Not Found", -1));
    }


    @RequestMapping(value = "/thing/last", method = RequestMethod.GET)
    @FlipOnDateTime(cutoffDateTimeProperty = "last.active.after")
    public Thing getLastThing(){
        return flipService.getLastThing();
    }

    @RequestMapping(value = "/thing/first", method = RequestMethod.GET)
    @FlipOnDateTime(cutoffDateTimeProperty = "first.active.after")
    public Thing getFirstThing(){
        return flipService.getLastThing();
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