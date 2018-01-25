package com.baeldung.dependencyinjections;

import org.springframework.stereotype.Service;

@Service
public class HomepageService {

    public String getContents() {
        return "Home page contents";
    }
}
