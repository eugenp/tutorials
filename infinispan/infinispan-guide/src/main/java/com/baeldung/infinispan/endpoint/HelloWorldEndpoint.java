package com.baeldung.infinispan.endpoint;

import com.baeldung.infinispan.service.HelloWorldService;
import com.baeldung.infinispan.service.TransactionalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldEndpoint {

    private HelloWorldService service;
    private TransactionalService transactionalService;

    public HelloWorldEndpoint(HelloWorldService service, TransactionalService transactionalService) {
        this.service = service;
        this.transactionalService = transactionalService;
    }

    @GetMapping("/simple-hello-world")
    public String getSimpleHelloWorld() {
        return service.findSimpleHelloWorld();
    }

    @GetMapping("/expiring-hello-world")
    public String getExpiringHelloWorld() {
        return service.findExpiringHelloWorld();
    }

    @GetMapping("/idle-hello-world")
    public String getIdleHelloWorld() {
        return service.findIdleHelloWorld();
    }

    @GetMapping("/hello-world-in-expiring-cache")
    public String getHelloWorldInExpiringCache() {
        return service.findSimpleHelloWorldInExpiringCache();
    }

    @GetMapping("/evicting-hello-world")
    public String getEvictingHelloWorld() {
        return service.findEvictingHelloWorld();
    }

    @GetMapping("/passivating-hello-world")
    public String getPassivatingHelloWorld() {
        return service.findPassivatingHelloWorld();
    }

    @GetMapping("/transactional")
    public String getTransactional() throws InterruptedException {
        Runnable backGroundJob = () -> transactionalService.startBackgroundBatch();
        Thread backgroundThread = new Thread(backGroundJob);

        transactionalService.getQuickHowManyVisits();
        backgroundThread.start();
        Thread.sleep(100); //lets wait our thread warm up
        transactionalService.getQuickHowManyVisits();
        return "OK";
    }

}
