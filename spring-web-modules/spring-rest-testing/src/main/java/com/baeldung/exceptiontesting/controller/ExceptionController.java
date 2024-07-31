package com.baeldung.exceptiontesting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.exceptiontesting.exception.BadArgumentsException;
import com.baeldung.exceptiontesting.exception.InternalException;
import com.baeldung.exceptiontesting.exception.ResourceNotFoundException;

@RestController
public class ExceptionController {

    @GetMapping("/exception/{exception_id}")
    public void getSpecificException(@PathVariable("exception_id") String pException) {
        if("not_found".equals(pException)) {
            throw new ResourceNotFoundException("resource not found");
        }
        else if("bad_arguments".equals(pException)) {
            throw new BadArgumentsException("bad arguments");
        }
        else {
            throw new InternalException("internal error");
        }
    }

    @GetMapping("/exception/throw")
    public void getException() throws Exception {
        throw new Exception("error");
    }
}
