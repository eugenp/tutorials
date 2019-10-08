package com.baeldung.debugging.consumer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Hooks;

@RestController
public class ReactiveConfigsToggleRestController {

    @GetMapping("/debug-hook-on")
    public String setReactiveDebugOn() {
        Hooks.onOperatorDebug();
        return "DEBUG HOOK ON";
    }

    @GetMapping("/debug-hook-off")
    public String setReactiveDebugOff() {
        Hooks.resetOnOperatorDebug();
        return "DEBUG HOOK OFF";
    }

}
