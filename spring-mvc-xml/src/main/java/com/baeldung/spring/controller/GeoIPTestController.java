package com.baeldung.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.spring.form.GeoIP;
import com.baeldung.spring.service.RawDBDemoGeoIPLocationService;

@Controller
public class GeoIPTestController {
    @RequestMapping(value="/GeoIPTest", method = RequestMethod.POST)
    @ResponseBody
    public GeoIP getLocation(
      @RequestParam(value="ipAddress", required=true) String ipAddress) throws Exception {
        RawDBDemoGeoIPLocationService locationService 
          = new RawDBDemoGeoIPLocationService();
        return locationService.getLocation(ipAddress);
    }
}
