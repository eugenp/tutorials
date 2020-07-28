package com.baldung.archunit.smurfs.presentation;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baldung.archunit.smurfs.service.SmurfsService;
import com.baldung.archunit.smurfs.service.dto.SmurfDTO;

@RequestMapping(value = "/smurfs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class SmurfsController {
    
    private SmurfsService smurfs;
    
    public SmurfsController(SmurfsService smurfs) {
        this.smurfs = smurfs;
    }

    @GetMapping
    public List<SmurfDTO> getSmurfs() {
        return smurfs.findAll();
    }
}
