package com.baeldung.spring.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.spring.form.GeoIP;
import com.baeldung.spring.service.RawDBDemoGeoIPLocationService;

//@Controller
//add db file and uncomment to see the working example
public class GeoIPTestController {
    private RawDBDemoGeoIPLocationService locationService;

    public GeoIPTestController() throws IOException {
        locationService = new RawDBDemoGeoIPLocationService();
    }

    @RequestMapping(value = "/GeoIPTest", method = RequestMethod.POST)
    @ResponseBody
    public GeoIP getLocation(@RequestParam(value = "ipAddress", required = true) String ipAddress) throws Exception {

        return locationService.getLocation(ipAddress);
    }
}
