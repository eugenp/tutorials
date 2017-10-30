package com.baeldung.diexamples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Phone {
    @Autowired
    private Disk disk;

    public String start() {
        return disk.read();
    }
}
