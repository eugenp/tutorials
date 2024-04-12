package com.baeldung.coverageaggregation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MyController {

    private final MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/tested")
    String fullyTested() {
        return myService.coveredByUnitAndIntegrationTests();
    }

    @GetMapping("/indirecttest")
    String indirectlyTestingServiceMethod() {
        return myService.coveredByIntegrationTest();
    }

    @GetMapping("/nottested")
    String notTested() {
        return myService.notTested();
    }

}