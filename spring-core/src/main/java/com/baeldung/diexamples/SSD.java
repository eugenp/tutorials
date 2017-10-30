package com.baeldung.diexamples;

import org.springframework.stereotype.Component;

@Component
public class SSD implements Disk {
    public String read() {
        return "Getting bytes from SSD, real fast!";
    }
}