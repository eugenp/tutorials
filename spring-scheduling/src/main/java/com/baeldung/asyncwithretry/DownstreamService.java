package com.baeldung.asyncwithretry;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DownstreamService {
    boolean publishEvents(List<String> events);
}