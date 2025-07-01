package com.baeldung.camel.producertemplate;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerTemplateController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @GetMapping("/send/simple/{message}")
    public String sendSimpleMessage(@PathVariable String message) {
        String response = producerTemplate.requestBody("direct:start", message, String.class);
        return response;
    }

    @GetMapping("/send/file/{message}")
    public String sendToFile(@PathVariable String message) {
        producerTemplate.sendBody("direct:fileRoute", message + "\n");
        return "Message appended to output.txt";
    }

    @GetMapping("/send/bean/{message}")
    public String sendToBean(@PathVariable String message) {
        String response = producerTemplate.requestBody("direct:beanRoute", message, String.class);
        return response;
    }

}
