package com.baeldung.dependencyinjections;

import org.springframework.stereotype.Service;

@Service
public class AboutuspageService {

    public String getContents() {
        return "Aboutus page contents";
    }
}
