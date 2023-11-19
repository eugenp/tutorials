package com.baeldung.boot.csfle.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.csfle.data.Citizen;
import com.baeldung.boot.csfle.service.CitizenService;

@RestController
@RequestMapping("/citizen")
public class CitizenController {

    private final CitizenService service;

    public CitizenController(CitizenService service) {
        this.service = service;
    }

    @GetMapping
    public List<Citizen> get() {
        return service.findAll();
    }

    @GetMapping("by")
    public Citizen getBy(@RequestParam String email) {
        return service.findByEmail(email);
    }

    @PostMapping
    public Object post(@RequestBody Citizen citizen) {
        return service.save(citizen);
    }
}
