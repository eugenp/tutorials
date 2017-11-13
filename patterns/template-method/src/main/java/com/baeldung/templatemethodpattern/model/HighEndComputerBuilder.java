package com.baeldung.templatemethodpattern.model;

public class HighEndComputerBuilder extends ComputerBuilder {

    @Override
    public void addMotherboard() {
        computerParts.put("Motherboard", "High-end Motherboard");
    }
    
    @Override
    public void setupMotherboard() {
        motherboardSetupStatus.add("Screwing the high-end motherboard to the case.");
        motherboardSetupStatus.add("Pluging in the power supply connectors.");
        motherboardSetupStatus.forEach(step -> System.out.println(step));
        moterboardSetupStatus.add("Screwing the high-end motherboard to the case.");
        moterboardSetupStatus.add("Pluging in the power supply connectors.");
        moterboardSetupStatus.forEach(step -> System.out.println(step));
    }
    
    @Override
    public void addProcessor() {
        computerParts.put("Processor", "High-end Processor");
        computerParts.put("Processor", "High-end Processor");
    }
}
