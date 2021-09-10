package com.example.hexagonalarchitecture.kitchenassistant.application.service;

import com.example.hexagonalarchitecture.kitchenassistant.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

    private final LoadUserPort userPort;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String name = userPort.createDummyUser();
        System.out.println("Dummy user --->> " + name);
    }
}