package com.baeldung.spring.cloud.kubernetes.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ClientConfig config;

    @Autowired
    private TravelAgencyService travelAgencyService;

    @RequestMapping("/deals")
    public String getDeals() {
        return travelAgencyService.getDeals();
    }

    @GetMapping
    public String load() {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://travel-agency-service:8080";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        String serviceList = "";
        if (discoveryClient != null) {
            List<String> services = this.discoveryClient.getServices();

            for (String service : services) {

                List<ServiceInstance> instances = this.discoveryClient.getInstances(service);

                serviceList += ("[" + service + " : " + ((!CollectionUtils.isEmpty(instances)) ? instances.size() : 0) + " instances ]");
            }
        }

        return String.format(config.getMessage(), response.getBody(), serviceList);
    }
}
