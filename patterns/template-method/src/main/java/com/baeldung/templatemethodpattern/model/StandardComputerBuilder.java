package com.baeldung.templatemethodpattern.model;
 
public class StandardComputerBuilder extends ComputerBuilder {

    @Override
    public void addMotherboard() {
        computerParts.put("Motherboard", "Standard Motherboard");
    }
    
    @Override
    public void setupMotherboard() {
        moterboardSetupStatus.add("Screwing the standard motherboard to the case.");
        moterboardSetupStatus.add("Pluging in the power supply connectors.");
        moterboardSetupStatus.forEach(step -> System.out.println(step));
    }
    
    @Override
    public void addProcessor() {
        computerParts.put("Processor", "Standard Processor");
    }
}
