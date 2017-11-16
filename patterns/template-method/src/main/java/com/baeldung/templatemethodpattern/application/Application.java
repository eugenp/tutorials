package com.baeldung.templatemethodpattern.application;

import com.baeldung.templatemethodpattern.model.Computer;
import com.baeldung.templatemethodpattern.model.StandardComputer;
import com.baeldung.templatemethodpattern.model.HighEndComputer;
import com.baeldung.templatemethodpattern.model.ComputerBuilder;
import com.baeldung.templatemethodpattern.model.HighEndComputerBuilder;
import com.baeldung.templatemethodpattern.model.StandardComputerBuilder;

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
