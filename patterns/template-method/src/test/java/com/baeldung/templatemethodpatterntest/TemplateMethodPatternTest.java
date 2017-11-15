package com.baeldung.templatemethodpatterntest;

import com.baeldung.templatemethodpattern.model.Computer;
import com.baeldung.templatemethodpattern.model.HighEndComputerBuilder;
import com.baeldung.templatemethodpattern.model.StandardComputerBuilder;
import com.baeldung.templatemethodpattern.model.HighEndComputer;
import com.baeldung.templatemethodpattern.model.StandardComputer;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class TemplateMethodPatternTest {

    private static StandardComputerBuilder standardComputerBuilder;
    private static HighEndComputerBuilder highEndComputerBuilder;

    @BeforeClass
    public static void setUpStandardComputerBuilderInstance() {
        standardComputerBuilder = new StandardComputerBuilder();
    }

    @BeforeClass
    public static void setUpHighEndComputerBuilderInstance() {
        highEndComputerBuilder = new HighEndComputerBuilder();
    }

    @Test
    public void givenStandardMotherBoard_whenAddingMotherBoard_thenEqualAssertion() {
        standardComputer.addMotherboard();
        assertEquals("Standard Motherboard", standardComputer.getComputerParts().get("Motherboard"));
    }

    @Test
    public void givenStandardMotheroboard_whenSetup_thenTwoEqualAssertions() {
        standardComputerBuilder.setupMotherboard();
        assertEquals("Screwing the standard motherboard to the case.", standardComputerBuilder.getMotherboardSetupStatus().get(0));
        assertEquals("Pluging in the power supply connectors.", standardComputerBuilder.getMotherboardSetupStatus().get(1));
    }

    @Test
    public void givenStandardProcessor_whenAddingProcessor_thenEqualAssertion() {
        standardComputerBuilder.addProcessor();
        assertEquals("Standard Processor", standardComputerBuilder.getComputerParts().get("Processor"));
    }

    @Test
    public void givenAllStandardParts_whenBuildingComputer_thenTwoParts() {
        standardComputerBuilder.buildComputer();
        assertEquals(2, standardComputerBuilder.getComputerParts().size());
    }
    
    @Test
    public void givenAllStandardParts_whenComputerisBuilt_thenComputerInstance() {
        assertThat(standardComputerBuilder.buildComputer(), instanceOf(Computer.class));
    }
    
    @Test
    public void givenHighEnddMotherBoard_whenAddingMotherBoard_thenEqualAssertion() {
        highEndComputerBuilder.addMotherboard();
        Assert.assertEquals("High-end Motherboard", highEndComputerBuilder.getComputerParts().get("Motherboard"));
    }

    @Test
    public void givenHighEnddMotheroboard_whenSetup_thenTwoEqualAssertions() {
        highEndComputerBuilder.setupMotherboard();
        assertEquals("Screwing the high-end motherboard to the case.", highEndComputerBuilder.getMotherboardSetupStatus().get(0));
        assertEquals("Pluging in the power supply connectors.", highEndComputerBuilder.getMotherboardSetupStatus().get(1));
    }

    @Test
    public void givenHightEndProcessor_whenAddingProcessor_thenEqualAssertion() {
        highEndComputerBuilder.addProcessor();
        assertEquals("High-end Processor", highEndComputerBuilder.getComputerParts().get("Processor"));
    }
    
    @Test
    public void givenAllHighEndParts_whenBuildingComputer_thenTwoParts() {
        highEndComputerBuilder.buildComputer();
        assertEquals(2, highEndComputerBuilder.getComputerParts().size());
    }
    
    @Test
    public void givenAllHighEndParts_whenComputerisBuilt_thenComputerInstance() {
        assertThat(standardComputerBuilder.buildComputer(), instanceOf(Computer.class));
    }
}
