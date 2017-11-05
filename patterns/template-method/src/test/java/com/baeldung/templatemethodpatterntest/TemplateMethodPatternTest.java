package com.baeldung.templatemethodpatterntest;

import com.baeldung.templatemethodpattern.model.HighEndComputer;
import com.baeldung.templatemethodpattern.model.StandardComputer;
import org.junit.Assert;
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
    public void givenStandardSoundCard_whenAddingSoundCard_thenEqualAssertion() {
        standardComputer.addSoundCard();
        Assert.assertEquals("Standard Sound Card", standardComputer
          .getComputerParts().get("Sound Card"));
    }
    
    @Test
    public void givenAllStandardParts_whenBuildingComputer_thenSixParts() {
        standardComputer.buildComputer();
        Assert.assertEquals(6, standardComputer
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
    public void givenHighEndMemory_whenAddingMemory_thenEqualAssertion() {
        highEndComputer.addMemory();
        Assert.assertEquals("16GB", highEndComputer
          .getComputerParts().get("Memory"));
    }
    
    @Test
    public void givenHighEndHardDrive_whenAddingHardDrive_thenEqualAssertion() {
        highEndComputer.addHardDrive();
        Assert.assertEquals("2TB Hard Drive", highEndComputer
          .getComputerParts().get("Hard Drive"));
    }
    
    @Test
    public void givenHighEndGraphicCard_whenAddingGraphicCard_thenEqualAssertion() {
        highEndComputer.addGraphicCard();
        Assert.assertEquals("High End Graphic Card", highEndComputer
          .getComputerParts().get("Graphic Card"));
    }
    
    @Test
    public void givenHighEndSoundCard_whenAddingSoundCard_thenEqualAssertion() {
        highEndComputer.addSoundCard();
        Assert.assertEquals("High End Sound Card", highEndComputer
          .getComputerParts().get("Sound Card"));
    }
    
    @Test
    public void givenAllHighEndParts_whenBuildingComputer_thenSixParts() {
        highEndComputer.buildComputer();
        Assert.assertEquals(6, highEndComputer.getComputerParts().size());
    }  
}
