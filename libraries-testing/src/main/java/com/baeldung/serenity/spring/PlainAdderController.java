package com.baeldung.serenity.spring;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author aiet
 */
@RequestMapping(value = "/adder", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class PlainAdderController {

    private final int currentNumber = RandomUtils.nextInt();

    @GetMapping("/current")
    public int currentNum() {
        return currentNumber;
    }

    @PostMapping
    public int add(@RequestParam int num) {
        return currentNumber + num;
    }

}
