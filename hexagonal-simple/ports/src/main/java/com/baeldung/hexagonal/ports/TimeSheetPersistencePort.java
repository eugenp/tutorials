package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.dtos.TimeSheetDto;

public interface TimeSheetPersistencePort {

    TimeSheetDto save(TimeSheetDto timeSheet);
    boolean hasUser(Long id);
    
    

}
