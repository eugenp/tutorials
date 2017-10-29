package com.baeldung.diexamples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Computer {
    private Disk disk;

    @Autowired
    public Computer(Disk disk) {
        this.disk = disk;
    }

    public String start() {
        return disk.read();
    }
}
