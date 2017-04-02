package com.baeldung.spring.di.constructor.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class DynaTrade {

    private @Autowired Deal deal;
    private @Autowired Stock stock;
    
}
