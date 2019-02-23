package com.baeldung.spring.cloud.kubernetes.services.travelagency.controller;

import org.springframework.web.bind.annotation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TravelAgencyController {

    private String[] deals = {"London - Paris : 25 Euro", "London - Frankfurt : 25 Euro"};
    private static final Log log = LogFactory.getLog(TravelAgencyController.class);


    @RequestMapping(method = POST, path = "/deals/{client}")
    public String deals(@PathVariable("client") String client) {
        log.info("Client: " + client + " is requesting new deals!");
        int randomDeal = new Random().nextInt(deals.length);
        return deals[randomDeal];
    }

    @RequestMapping(method = GET, path = "/")
    @ResponseBody
    public String get() throws UnknownHostException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Host: ").append(InetAddress.getLocalHost().getHostName()).append("<br/>");
        stringBuilder.append("IP: ").append(InetAddress.getLocalHost().getHostAddress()).append("<br/>");
        stringBuilder.append("Type: ").append("Travel Agency").append("<br/>");
        return stringBuilder.toString();
    }
}
