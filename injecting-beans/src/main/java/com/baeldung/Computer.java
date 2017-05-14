package com.baeldung;

import com.baeldung.model.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Model for Computer.
 */
public class Computer {

    @Autowired
    private GraphicsCard graphicsCard;
    private Processor processor;
    private HardDisk hardDisk;
    private Screen screen;
    private OperatingSystem operatingSystem;

    @Autowired
    public Computer(Processor processor, OperatingSystem operatingSystem, HardDisk hardDisk) {
        this.operatingSystem = operatingSystem;
        this.processor = processor;
        this.hardDisk = hardDisk;
    }

    public void setHardDisk(HardDisk hardDisk) {
        this.hardDisk = hardDisk;
    }

    @Autowired
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void print() {
        System.out.println("Injected processor: " + processor.getName());
        System.out.println("Injected hard disk: " + hardDisk.getName());
        System.out.println("Injected graphics card: " + graphicsCard.getName());
        System.out.println("Injected screen size: " + screen.getSize());
        System.out.println("Injected operating system name: " + operatingSystem.getName());
    }

    @Override
    public String toString() {
        return "Computer{" +
                "graphicsCard=" + graphicsCard +
                ", processor=" + processor +
                ", hardDisk=" + hardDisk +
                ", screen=" + screen +
                ", operatingSystem=" + operatingSystem +
                '}';
    }
}
