package com.baeldung.templatemethodpattern.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ComputerBuilder {

    protected Map<String, String> computerParts = new HashMap<>();
    protected List<String> moterboardSetupStatus = new ArrayList<>();
    
    public final Computer buildComputer() {
        addMotherboard();
        setupMotherboard();
        addProcessor();
        return getComputer();
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
    
    private Computer getComputer() {
        return new Computer(computerParts);
    }
}
