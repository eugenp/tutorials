package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.entity.ScheduleEntity;

import java.util.List;

public interface ScheduleService {

    List<ScheduleEntity> findAll();

}
