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

    public void start() {
        System.out.println("Booting...");
        System.out.println(disk.read());
    }
}
