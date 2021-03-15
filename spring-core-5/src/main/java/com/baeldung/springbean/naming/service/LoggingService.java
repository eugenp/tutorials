package com.baeldung.springbean.naming.service;

import com.baeldung.springbean.naming.gateway.LoggingGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    @Autowired
    private LoggingGateway gateway;
}
