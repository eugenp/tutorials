package com.baeldung.spring.enums.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.enums.entity.ModeEntity;
import com.baeldung.spring.model.Modes;

@RestController
@RequestMapping("/enums")
public class EnumController {

    @GetMapping("/mode2str")
    public String getStringToMode(@RequestParam("mode") Modes mode) {
        return "good";
    }

    @GetMapping("/findbymode/{mode}")
    public String findByEnum(@PathVariable Modes mode) {
        return "good";
    }

    @PostMapping("/create-modes")
    public String create(@RequestBody ModeEntity entity) {
        return "created";
    }

}