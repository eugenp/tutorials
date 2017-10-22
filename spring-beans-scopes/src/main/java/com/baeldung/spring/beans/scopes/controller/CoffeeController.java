package com.baeldung.spring.beans.scopes.controller;

import com.baeldung.spring.beans.scopes.domain.Coffee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shazi on 10/21/2017.
 */
@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private Coffee brewedOncePerDay;

    @Autowired
    private Coffee freshlyBrewedOnLocalRequest;

    @Autowired
    private Coffee onRequestBrewed;

    @Autowired
    private Coffee perSessionBrewed;

    @Autowired
    private Coffee perGlobalSessionBrewed;

    @GetMapping("/from/morningbrewed")
    public String morning() {
        return brewedOncePerDay.getType() + " at " + brewedOncePerDay.getBrewedTime();
    }

    @GetMapping("/from/freshlybrewed")
    public String fresh() {
        return freshlyBrewedOnLocalRequest.getType() + " at " + freshlyBrewedOnLocalRequest.getBrewedTime();
    }

    @GetMapping("/from/onlinerequestbrewed")
    public String onRequestBrewed() {
        return onRequestBrewed.getType() + " at " + onRequestBrewed.getBrewedTime();
    }

    @GetMapping("/from/onlinesessionbrewed")
    public String perSessionBrewed() {
        return perSessionBrewed.getType() + " at " + perSessionBrewed.getBrewedTime();
    }

    @GetMapping("/from/onlineglobalsessionbrewed")
    public String perGlobalSessionBrewed() {
        return perGlobalSessionBrewed.getType() + " at " + perGlobalSessionBrewed.getBrewedTime();
    }
}
