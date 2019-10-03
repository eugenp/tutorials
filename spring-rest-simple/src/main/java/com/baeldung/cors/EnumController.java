package com.baeldung.cors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.model.Modes;

@CrossOrigin(maxAge = 3600)
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
}