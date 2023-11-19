package com.baeldung.requestheader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.requestheader.interceptor.OperatorHolder;

@RestController
public class BuzzController {
    private final OperatorHolder operatorHolder;

    public BuzzController(OperatorHolder operatorHolder) {
        this.operatorHolder = operatorHolder;
    }

    @GetMapping("buzz")
    public String buzz() {
        return "hello, " + operatorHolder.getOperator();
    }

}
