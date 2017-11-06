package com.baeldung.templatemethodpattern.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Computer {
    
    protected Map<String, String> computerParts = new HashMap<>();
    protected List<String> moterboardSetupStatus = new ArrayList<>();
    
    public final void buildComputer() {
         addMotherboard();
         setupMotherboard();
         addProcessor();
    }
    
    public abstract void addMotherboard();
    
     public abstract void setupMotherboard();
    
    public abstract void addProcessor();
    
    public List<String> getMotherboardSetupStatus() {
        return moterboardSetupStatus;
    }
    
    public Map<String, String> getComputerParts() {
        return computerParts;
    }   
}
