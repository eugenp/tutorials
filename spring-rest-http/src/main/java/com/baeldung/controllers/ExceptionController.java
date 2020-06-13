package com.baeldung.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.exception.BadArgumentsException;
import com.baeldung.exception.InternalException;
import com.baeldung.exception.ResourceNotFoundException;

@RestController
public class ExceptionController {
    
    @GetMapping(path="/exception/{exception_id}")
    public void getSpecificException(@PathVariable("exception_id") String pException) {
        if(pException.equals("not_found"))
            throw new ResourceNotFoundException("resource not found");
        else if(pException.equals("bad_arguments"))
            throw new BadArgumentsException("bad arguments");
        else
            throw new InternalException("internal error");
    }
    
    @GetMapping(path="/exception/throw")
    public String getException() throws Exception {
        throw new Exception();
    }
}
