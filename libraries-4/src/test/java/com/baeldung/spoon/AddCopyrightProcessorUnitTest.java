package com.baeldung.spoon;

import org.junit.Test;

import spoon.Launcher;
import spoon.SpoonAPI;

public class AddCopyrightProcessorUnitTest {
    
    @Test
    public void whenAddCopyright_thenSuccess() {
        
        SpoonAPI spoon = new Launcher();
        spoon.addProcessor(new AddCopyrightProcessor());
        spoon.addInputResource("src/test/resources/spoon/SpoonClassToTest.java");
        spoon.setSourceOutputDirectory("./target/spoon-processed");
        spoon.buildModel();
        spoon.process();
        spoon.prettyprint();
                   
    }

}
