package com.baeldung.spoon;

import org.junit.Test;

public class AddCopyrightTransformerUnitTest {
   
    
    
    @Test
    public void whenAddCopyright_thenSuccess() {
        
        AddCopyrightTransformer transformer = new AddCopyrightTransformer();
        transformer.addCopyright("src/test/resources/spoon/SpoonClassToTest.java");
        
    }

}
