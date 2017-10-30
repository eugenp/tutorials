package com.baeldung.templatemethodpattern.model;

public class HighEndComputer extends Computer {

    @Override
    public void addProcessor() {
         computerParts.put("Processor", "High End Processor");
    }

    @Override
    public void addMotherboard() {
         computerParts.put("Motherboard", "High End Motherboard");
    }

    @Override
    public void addMemory() {
         computerParts.put("Memory", "16GB");
    }

    @Override
    public void addHardDrive() {
         computerParts.put("Hard Drive", "2TB Hard Drive");
    }

    @Override
    public void addGraphicCard() {
         computerParts.put("Graphic Card", "High End Graphic Card");
    }

    @Override
    public void addSoundCard() {
         computerParts.put("Sound Card", "High End Sound Card");
    }  
}
