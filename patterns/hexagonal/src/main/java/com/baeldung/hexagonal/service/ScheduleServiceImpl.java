package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.entity.ScheduleEntity;
import com.baeldung.hexagonal.repository.ScheduleRepository;
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