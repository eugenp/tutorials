package com.baeldung.resourcebundle;

import java.util.ListResourceBundle;

public class ExampleResource_pl_PL extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] { 
            { "currency", "polish zloty" }, 
            { "toUsdRate", new Double("3.401") }, 
            { "cities", new String[] { "Warsaw", "Cracow" } } 
        };
    }

}
