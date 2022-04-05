package com.baeldung.flips.controller;

import com.baeldung.flips.model.Foo;
import com.baeldung.flips.service.FlipService;
import org.flips.annotation.FlipOnDateTime;
import org.flips.annotation.FlipOnDaysOfWeek;
import org.flips.annotation.FlipOnEnvironmentProperty;
import org.flips.annotation.FlipOnProfiles;
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

    @RequestMapping(value = "/foos", method = RequestMethod.GET)
    @FlipOnProfiles(activeProfiles = "dev")
    public List<Foo> getAllFoos() {
        return flipService.getAllFoos();
    }

    @RequestMapping(value = "/foo/{id}", method = RequestMethod.GET)
    @FlipOnDaysOfWeek(daysOfWeek = {
        DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
    })
    public Foo getFooByNewId(@PathVariable int id) {
        return flipService.getFooById(id).orElse(new Foo("Not Found", -1));
    }

    @RequestMapping(value = "/foo/last", method = RequestMethod.GET)
    @FlipOnDateTime(cutoffDateTimeProperty = "last.active.after")
    public Foo getLastFoo() {
        return flipService.getLastFoo();
    }

    @RequestMapping(value = "/foo/first", method = RequestMethod.GET)
    @FlipOnDateTime(cutoffDateTimeProperty = "first.active.after")
    public Foo getFirstFoo() {
        return flipService.getLastFoo();
    }

    @RequestMapping(value = "/foos/{id}", method = RequestMethod.GET)
    @FlipOnEnvironmentProperty(property = "feature.foo.by.id", expectedValue = "Y")
    public Foo getFooById(@PathVariable int id) {
        return flipService.getFooById(id).orElse(new Foo("Not Found", -1));
    }

    @RequestMapping(value = "/foo/new", method = RequestMethod.GET)
    public Foo getNewThing() {
        return flipService.getNewFoo();
    }
}