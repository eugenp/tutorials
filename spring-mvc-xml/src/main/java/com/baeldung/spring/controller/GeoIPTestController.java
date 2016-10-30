package com.baeldung.spring.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.spring.form.GeoIP;
import com.maxmind.geoip2.exception.GeoIp2Exception;

@Controller
public class GeoIPTestController {

    @RequestMapping(value="/GeoIPTest", method = RequestMethod.POST) 
    public ResponseEntity getLocation(@ModelAttribute GeoIP geoIP) {
        try {
            geoIP.getLocationFromIp();
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
        }
        
        return new ResponseEntity<>(geoIP, HttpStatus.OK);
    }
}
