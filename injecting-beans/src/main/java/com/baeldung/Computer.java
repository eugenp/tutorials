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

    public Computer(Processor processor) {
        this.processor = processor;
    }

    @Autowired
    public Computer(OperatingSystem operatingSystem, Processor processor) {
        this.operatingSystem = operatingSystem;
        this.processor = processor;
    }

    public void setHardDisk(HardDisk hardDisk) {
        this.hardDisk = hardDisk;
    }

    @Autowired
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void print() {
        System.out.println("Injected processor by constructor injection: " + processor.getName());
        System.out.println("Injected hard disk by setter injection: " + hardDisk.getName());
        System.out.println("Injected graphics card with field annotation: " + graphicsCard.getName());
        System.out.println("Injected screen with setter annotation: " + screen.getSize());
        System.out.println("Injected operating system name with constructor annotation: " + operatingSystem.getName());
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
