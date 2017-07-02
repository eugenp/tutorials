package com.baeldung.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HystrixController {

    @Autowired
    private SpringExistingClient client;

    @RequestMapping("/withHystrix")
    public String withHystrix() throws InterruptedException{
        return client.invokeRemoteServiceWithHystrix();
    }

    @RequestMapping("/withOutHystrix")
    public String withOutHystrix() throws InterruptedException{
        return client.invokeRemoteServiceWithOutHystrix();
    }
}
