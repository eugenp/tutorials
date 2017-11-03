package com.baeldung.templatemethodpattern.model;
 
public class StandardComputer extends Computer {

    @Override
    public void addProcessor() {
        computerParts.put("Processor", "Standard Processor");
    }

    @Override
    public void addMotherboard() {
        computerParts.put("Motherboard", "Standard Motherboard");
    }

    @Override
    public void addMemory() {
        computerParts.put("Memory", "8GB");
    }

    @Override
    public void addHardDrive() {
        computerParts.put("Hard Drive", "1TB Hard Drive");
    }

    @Override
    public void addGraphicCard() {
        computerParts.put("Graphic Card", "Standard Graphic Card");
    }

    @Override
    public void addSoundCard() {
        computerParts.put("Sound Card", "Standard Sound Card");
    }
}
