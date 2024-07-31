package com.baeldung.structurizr.spring;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class PaymentController {
    @Resource
    private PaymentRepository repository;

    @Resource
    private GenericComponent component;
}
