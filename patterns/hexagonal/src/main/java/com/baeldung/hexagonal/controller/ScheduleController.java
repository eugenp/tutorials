package com.example.hexagonal.controller;

import com.example.hexagonal.entity.ScheduleEntity;
import com.example.hexagonal.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/schedules")
    public List<ScheduleEntity> findAll() {
        return scheduleService.findAll();
    }
}
