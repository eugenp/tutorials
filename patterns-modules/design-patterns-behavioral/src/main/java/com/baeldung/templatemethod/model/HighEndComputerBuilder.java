package com.baeldung.templatemethod.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HighEndComputerBuilder extends ComputerBuilder {

    @Override
    public void addMotherboard() {
        computerParts.put("Motherboard", "High-end Motherboard");
    }
    
    @Override
    public void setupMotherboard() {
        motherboardSetupStatus.add("Screwing the high-end motherboard to the case.");
        motherboardSetupStatus.add("Pluging in the power supply connectors.");
        motherboardSetupStatus.forEach(step -> log.debug(step));
    }
    
    @Override
    public void addProcessor() {
        computerParts.put("Processor", "High-end Processor");
    }
}
