package com.baeldung.hexagonal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_CLASS)
public class HexagonalArchitectureTest {

    private DataPort h2DBAdapter;
    private DataPort flatFileAdapter;
    
    @BeforeAll
    public void setUp() {
        h2DBAdapter = new H2DatabaseAdapter();;
        flatFileAdapter = new XMLFileAdapter();
    }

    @Test
    public void whenCoffeeRecipeInH2Database_makeCoffee() {
        
        CoreApplication coreApplication = new CoreApplication();
        coreApplication.setDataSource(h2DBAdapter);
        
        assertTrue(coreApplication.makeCoffee("latte"));
        
        assertTrue(coreApplication.makeCoffee("espresso"));
        
        //Not saved in H2 DB
        assertFalse(coreApplication.makeCoffee("mocha"));

    }
    
    @Test
    public void whenCoffeeRecipeInMockFile_makeCoffee() {

        CoreApplication coreApplication = new CoreApplication();
        coreApplication.setDataSource(flatFileAdapter);

        assertTrue(coreApplication.makeCoffee("cappuccino"));

        //Not saved in data file
        assertFalse(coreApplication.makeCoffee("macchiato"));

    }
}