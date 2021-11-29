package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.dtos.TimeSheetDto;
import com.baeldung.hexagonal.exceptions.TimeSheetConflictException;
import com.spencerwi.either.Either;

public interface TimeSheetServicePort{

   Either<TimeSheetConflictException, TimeSheetDto> save(TimeSheetDto dto) throws TimeSheetConflictException;
}
