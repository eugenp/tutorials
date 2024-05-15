package com.baeldung.parent;

import org.springframework.stereotype.Service;

@Service
public class HomeService implements IHomeService {

    public String getGreeting() {
        return "Welcome User";
    }
}
