package com.baeldung.micronaut.micronautjunit;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller
public class AdditionController {

    private final AdditionService additionService;

    public AdditionController(AdditionService additionService) {
        this.additionService = additionService;
    }

    @Get(uri = "/sum", produces = MediaType.TEXT_PLAIN)
    public Integer sum(@QueryValue("firstNumber") int firstNumber, @QueryValue("secondNumber") int secondNumber) {
        return additionService.sum(firstNumber, secondNumber);
    }
}
