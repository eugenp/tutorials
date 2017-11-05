package com.baeldung.templatemethodpattern.model;

import java.util.HashMap;
import java.util.Map;

public abstract class Computer {
    
    protected Map<String, String> computerParts = new HashMap<>();
    
    public final void buildComputer() {
         addMotherboard();   
         addProcessor();
         addMemory();
         addHardDrive();
         addGraphicCard();
         addSoundCard();        
    }
    
    public abstract void addProcessor();
    
    public abstract void addMotherboard();
    
    public abstract void addMemory();
    
    public abstract void addHardDrive();
    
    public abstract void addGraphicCard();
    
    public abstract void addSoundCard();
    
    public Map<String, String> getComputerParts() {
        return computerParts;
    }   
}
