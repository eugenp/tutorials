package com.baeldung.hexagonalarchitecturejava.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartBeat
{
    @RequestMapping("heartbeat")
    public String heartbeat()
    {
        return "Always up and running for you";
    }
}
