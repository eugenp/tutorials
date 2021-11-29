package com.baeldung.hexagonal.services;

import java.time.LocalDateTime;

import com.baeldung.hexagonal.dtos.TimeSheetDto;
import com.baeldung.hexagonal.exceptions.TimeSheetConflictException;
import com.baeldung.hexagonal.ports.TimeSheetPersistencePort;
import com.baeldung.hexagonal.ports.TimeSheetServicePort;
import com.spencerwi.either.Either;

public class TimeSheetService implements TimeSheetServicePort {

	
    private TimeSheetPersistencePort timeSheetDataBasePort;
    
    
    public TimeSheetService(TimeSheetPersistencePort timeSheetDataBasePort) {
        this.timeSheetDataBasePort = timeSheetDataBasePort;
    }


	public Either<TimeSheetConflictException, TimeSheetDto> save(TimeSheetDto dto) {
		if(hasUser(dto)) {
			dto.setRegisteredAt(LocalDateTime.now());
			return  Either.right(timeSheetDataBasePort.save(dto));
		}else {
			return Either.left(new TimeSheetConflictException("User not Found"));
		}
	}
	
	private boolean hasUser(TimeSheetDto dto) {
		return timeSheetDataBasePort.hasUser(dto.getUserId());
	}



   
}
