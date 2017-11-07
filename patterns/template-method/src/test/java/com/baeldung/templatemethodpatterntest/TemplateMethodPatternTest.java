package com.baeldung.templatemethodpatterntest;

import com.baeldung.templatemethodpattern.model.HighEndComputer;
import com.baeldung.templatemethodpattern.model.StandardComputer;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class TemplateMethodPatternTest {

    private static StandardComputer standardComputer;
    private static HighEndComputer highEndComputer;
    
    @BeforeClass
    public static void setUpStandardComputerInstance() {
        standardComputer = new StandardComputer();
    }
    
    @BeforeClass
    public static void setUpHighEndComputerInstance() {
        highEndComputer = new HighEndComputer();
    }

    @Test
    public void givenStandardMotherBoard_whenAddingMotherBoard_thenEqualAssertion() {
        standardComputer.addMotherboard();
        assertEquals("Standard Motherboard", standardComputer.getComputerParts().get("Motherboard"));
    }

    @Test
    public void givenStandardMotheroboard_whenSetup_thenTwoEqualAssertions() {
        standardComputer.setupMotherboard();
        assertEquals("Screwing the standard motherboard to the case.", standardComputer.getMotherboardSetupStatus().get(0));
        assertEquals("Plugin in the power supply connectors.",  standardComputer.getMotherboardSetupStatus().get(1));
    }
    
    @Test
    public void givenStandardProcessor_whenAddingProcessor_thenEqualAssertion() {
        standardComputer.addProcessor();
        assertEquals("Standard Processor", standardComputer.getComputerParts().get("Processor"));
    }

    @Test
    public void givenAllStandardParts_whenBuildingComputer_thenTwoParts() {
        standardComputer.buildComputer();
        assertEquals(2, standardComputer.getComputerParts().size());
    }

    @Test
    public void givenHighEnddMotherBoard_whenAddingMotherBoard_thenEqualAssertion() {
        highEndComputer.addMotherboard();
        Assert.assertEquals("High-end Motherboard", highEndComputer.getComputerParts().get("Motherboard"));
    }
    
    @Test
    public void givenHighEnddMotheroboard_whenSetup_thenTwoEqualAssertions() {
        highEndComputer.setupMotherboard();
        assertEquals("Screwing the high-end motherboard to the case.", highEndComputer.getMotherboardSetupStatus().get(0));
        assertEquals("Plugin in the power supply connectors.", highEndComputer.getMotherboardSetupStatus().get(1));
    }
    
    @Test
    public void givenHightEndProcessor_whenAddingProcessor_thenEqualAssertion() {
        highEndComputer.addProcessor();
        Assert.assertEquals("High-end Processor", highEndComputer.getComputerParts().get("Processor"));
    }
    
    @Test
    public void givenAllHighEnddParts_whenBuildingComputer_thenTwoParts() {
        highEndComputer.buildComputer();
        assertEquals(2, highEndComputer.getComputerParts().size());
    }
    
    @Test
    public void givenStandardProcessor_whenAddingProcessor_thenEqualAssertion() {
        standardComputer.addProcessor();
        Assert.assertEquals("Standard Processor", standardComputer
          .getComputerParts().get("Processor"));
    }
    
    @Test
    public void givenStandardMotherBoard_whenAddingMotherBoard_thenEqualAssertion() {
        standardComputer.addMotherboard();
        Assert.assertEquals("Standard Motherboard", standardComputer
          .getComputerParts().get("Motherboard"));
    }
    
    @Test
    public void givenStandardMemory_whenAddingMemory_thenEqualAssertion() {
        standardComputer.addMemory();
        Assert.assertEquals("8GB", standardComputer
          .getComputerParts().get("Memory"));
    }
    
    @Test
    public void givenStandardHardDrive_whenAddingHardDrive_thenEqualAssertion() {
        standardComputer.addHardDrive();
        Assert.assertEquals("1TB Hard Drive", standardComputer
          .getComputerParts().get("Hard Drive"));
    }
    
    @Test
    public void givenStandardGraphicaCard_whenAddingGraphicCard_thenEqualAssertion() {
        standardComputer.addGraphicCard();
        Assert.assertEquals("Standard Graphic Card", standardComputer
          .getComputerParts().get("Graphic Card"));
    }
    
    @Test
    public void givenAllStandardParts_whenBuildingComputer_thenTwoParts() {
        standardComputer.buildComputer();
        Assert.assertEquals(2, standardComputer
          .getComputerParts().size());
    }
    
    @Test
    public void givenHightEndProcessor_whenAddingProcessor_thenEqualAssertion() {
        highEndComputer.addProcessor();
        Assert.assertEquals("High End Processor", highEndComputer
          .getComputerParts().get("Processor"));
    }
    
    @Test
    public void givenHighEnddMotherBoard_whenAddingMotherBoard_thenEqualAssertion() {
        highEndComputer.addMotherboard();
        Assert.assertEquals("High End Motherboard", highEndComputer
          .getComputerParts().get("Motherboard"));
    }
    
    @Test
    public void givenAllHighEndParts_whenBuildingComputer_thenTwoParts() {
        highEndComputer.buildComputer();
        Assert.assertEquals(2, highEndComputer.getComputerParts().size());
    }  
}
