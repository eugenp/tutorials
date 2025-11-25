package com.baeldung.example.apiversioning.controller.negotiation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserContentNegotiationController {

    @GetMapping(value = "/negotiation", produces = "application/vnd.col.users.v1+json")
    public String getUsersV1() {
        return "User list v1";
    }

    @GetMapping(value = "/negotiation", produces = "application/vnd.col.users.v2+json")
    public String getUsersV2() {
        return "User list v2";
    }
}