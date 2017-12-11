package com.baeldung.pattern.templatemethod.application;

import com.baeldung.pattern.templatemethod.model.Computer;
import com.baeldung.pattern.templatemethod.model.StandardComputer;
import com.baeldung.pattern.templatemethod.model.HighEndComputer;
import com.baeldung.pattern.templatemethod.model.ComputerBuilder;
import com.baeldung.pattern.templatemethod.model.HighEndComputerBuilder;
import com.baeldung.pattern.templatemethod.model.StandardComputerBuilder;

public class Application {
    
    public static void main(String[] args) {
        ComputerBuilder standardComputerBuilder = new StandardComputerBuilder();
        Computer standardComputer = standardComputerBuilder.buildComputer();
        standardComputer.getComputerParts().forEach((k, v) -> System.out.println("Part : " + k + " Value : " + v));
        
        ComputerBuilder highEndComputerBuilder = new HighEndComputerBuilder();
        Computer highEndComputer = highEndComputerBuilder.buildComputer();
        highEndComputer.getComputerParts().forEach((k, v) -> System.out.println("Part : " + k + " Value : " + v));
    }
}
