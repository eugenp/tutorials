package com.baeldung.metrics.micrometer;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author aiet
 */
@RestController
@Timed("people")
public class PeopleController {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @GetMapping("/people")
    @Timed(value = "people.all", longTask = true)
    public List<String> listPeople() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(SECURE_RANDOM.nextInt(10000));
        return Arrays.asList("Jim", "Tom", "Tim");
    }

}
