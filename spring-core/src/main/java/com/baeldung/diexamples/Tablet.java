package com.baeldung.diexamples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Tablet {
    private Disk disk;

    @Autowired
    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public void start() {
        System.out.print("Booting...");
        System.out.print(disk.read());
    }
}
