package com.baeldung.enummapping.controllers;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.enummapping.editors.LevelEditor;
import com.baeldung.enummapping.enums.Level;

@RestController
@RequestMapping("enummapping")
public class EnumMappingController {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Level.class, new LevelEditor());
    }

    @GetMapping("/get")
    public String getByLevel(@RequestParam(required = false) Level level) {
        if (level != null) {
            return level.name();
        }
        return "undefined";
    }

}
