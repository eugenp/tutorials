package com.baeldung.hexagonal.database.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.dtos.TimeSheetDto;
import com.baeldung.hexagonal.entities.TimeSheet;
import com.baeldung.hexagonal.ports.TimeSheetPersistencePort;
import com.baeldung.hexagonal.repositories.TimeSheetRepository;
import com.baeldung.hexagonal.repositories.UserRepository;

@Service
public class TimeSheetPersistencePortImpl implements TimeSheetPersistencePort {
	
	@Autowired
	TimeSheetRepository tRepository;
	@Autowired
	UserRepository uRepository;

	@Override
	public TimeSheetDto save(TimeSheetDto timeSheet) {
		TimeSheet entSheet = tRepository.save(TimeSheet.builder().User(uRepository.findById(timeSheet.getUserId()).get())
										   .registeredAt(timeSheet.getRegisteredAt())
										   .typeRegitry(timeSheet.getRegisterType()).build());
		timeSheet.setId(entSheet.getId());
		return timeSheet;
		
	}

	@Override
	public boolean hasUser(Long id) {
		return uRepository.findById(id).isPresent();
	}


}
