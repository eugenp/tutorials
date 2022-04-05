package com.baeldung.longpolling.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.String.format;

/**
 * Long polling controller example.
 */
@RestController
@RequestMapping("/api")
public class BakeryController {
    private final static Logger LOG = LoggerFactory.getLogger(BakeryController.class);
    private final static Long LONG_POLLING_TIMEOUT = 5000L;

    private ExecutorService bakers;

    public BakeryController() {
        bakers = Executors.newFixedThreadPool(5);
    }

    @GetMapping("/bake/{bakedGood}")
    public DeferredResult<String> publisher(@PathVariable String bakedGood, @RequestParam Integer bakeTime) {

        DeferredResult<String> output = new DeferredResult<>(LONG_POLLING_TIMEOUT);

        bakers.execute(() -> {
            try {
                Thread.sleep(bakeTime);
                output.setResult(format("Bake for %s complete and order dispatched. Enjoy!", bakedGood));
            } catch (Exception e) {
                output.setErrorResult("Something went wrong with your order!");
            }
        });

        output.onTimeout(() -> output.setErrorResult("the bakery is not responding in allowed time"));
        return output;
    }
}
