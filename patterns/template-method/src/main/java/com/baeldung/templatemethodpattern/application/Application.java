package com.baeldung.templatemethodpattern.application;

import com.baeldung.templatemethodpattern.model.Computer;
import com.baeldung.templatemethodpattern.model.HighEndComputer;
import com.baeldung.templatemethodpattern.model.StandardComputer;

public class Application {
    
    public static void main(String[] args) {
        Computer standardComputer = new StandardComputer();
        standardComputer.buildComputer();
        standardComputer.getComputerParts().forEach((k, v) -> System.out.println("Part : " + k + " Value : " + v));
        
        Computer highEndComputer = new HighEndComputer();
        highEndComputer.buildComputer();
        highEndComputer.getComputerParts().forEach((k, v) -> System.out.println("Part : " + k + " Value : " + v));
    }
}
