package com.example.hexagonal.service;

import com.example.hexagonal.entity.ScheduleEntity;
import com.example.hexagonal.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<ScheduleEntity> findAll() {
        return scheduleRepository.findAll();
    }
}


//    @PostConstruct
//    public void init() {
//        scheduleRepository.save(new ScheduleEntity(1L, Instant.now()));
//    }