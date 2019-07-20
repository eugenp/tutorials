package com.baeldung.hexagonal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_CLASS)
public class HexagonalArchitectureTest {

    private DataPort h2DBPortImpl;
    private DataPort flatFileImpl;
    
    @BeforeAll
    public void setUp() {
        h2DBPortImpl = new H2DatabaseAdapter();;
        flatFileImpl = new FlatFileAdapter();
    }

    @Test
    public void whenCoffeeRecepieInH2Database_makeCoffee() {
        
        CoreApplication coreApplication = new CoreApplication(h2DBPortImpl);
        
        assertTrue(coreApplication.makeCoffee("latte"));
        
        assertTrue(coreApplication.makeCoffee("espresso"));
        
        //Not saved in H2 DB
        assertFalse(coreApplication.makeCoffee("mocha"));

    }
    
    @Test
    public void whenCoffeeRecepieInMockFile_makeCoffee() {

        CoreApplication coreApplication = new CoreApplication(flatFileImpl);

        assertTrue(coreApplication.makeCoffee("cappuccino"));

        //Not saved in data file
        assertFalse(coreApplication.makeCoffee("macchiato"));

    }
}