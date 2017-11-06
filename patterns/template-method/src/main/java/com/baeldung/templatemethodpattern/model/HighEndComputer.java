package com.baeldung.templatemethodpattern.model;

public class HighEndComputer extends Computer {

    @Override
    public void addMotherboard() {
         computerParts.put("Motherboard", "High-end Motherboard");
    }
    
    @Override
    public void setupMotherboard() {
        moterboardSetupStatus.add("Screwing the high-end motherboard to the case.");
        moterboardSetupStatus.add("Pluging in the power supply connectors.");
        moterboardSetupStatus.forEach(step -> System.out.println(step));
    }
    
    @Override
    public void addProcessor() {
         computerParts.put("Processor", "High-end Processor");
    }
}
