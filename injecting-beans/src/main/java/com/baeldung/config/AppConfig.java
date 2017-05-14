package com.baeldung.config;

import com.baeldung.Computer;
import com.baeldung.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Beans configuration.
 */
@Configuration
public class AppConfig {

    @Bean(name = "computer")
    public Computer computer() {
        return new Computer(processor(), operatingSystem(), hardDisk());
    }

    @Bean(name = "processor")
    public Processor processor() {
        Processor processor = new Processor();
        processor.setName("Intel Core i7-6650U");
        return processor;
    }

    @Bean(name = "hardDisk")
    public HardDisk hardDisk() {
        HardDisk hardDisk = new HardDisk();
        hardDisk.setName("SSD TRANSCEND MTS400");
        return hardDisk;
    }

    @Bean(name = "graphicsCard")
    public GraphicsCard graphicsCard() {
        GraphicsCard graphicsCard = new GraphicsCard();
        graphicsCard.setName("NVIDIA GeForce GTX980M");
        return graphicsCard;
    }

    @Bean(name = "operatingSystem")
    public OperatingSystem operatingSystem() {
        OperatingSystem operatingSystem = new OperatingSystem();
        operatingSystem.setName("Windows 10 Pro");
        return operatingSystem;
    }

    @Bean(name = "screen")
    public Screen screen() {
        Screen screen = new Screen();
        screen.setSize("17 inches");
        return screen;
    }

}
