package com.baeldung.hexagonal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.dtos.TimeSheetDto;
import com.baeldung.hexagonal.exceptions.TimeSheetConflictException;
import com.baeldung.hexagonal.ports.TimeSheetServicePort;
import com.spencerwi.either.Either;

@RestController
@RequestMapping("/timesheet")
public class TimeSheetController {

    @Autowired
    private TimeSheetServicePort servicePort;

    @PostMapping("/")
    public ResponseEntity<TimeSheetDto> save(@RequestBody TimeSheetDto timeSheetDto) throws TimeSheetConflictException {
        
    	Either<TimeSheetConflictException, TimeSheetDto> returnSave = servicePort.save(timeSheetDto);
    	
    	if(returnSave.isRight()) {
    		return ResponseEntity.status(HttpStatus.CREATED).body(returnSave.getRight());
    	}else {
    		throw returnSave.getLeft();
    	}
        
    }

   
}
