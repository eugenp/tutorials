package com.example.hexagonal.service;

import com.example.hexagonal.entity.ScheduleEntity;

import java.util.List;

public interface ScheduleService {

    List<ScheduleEntity> findAll();

}
