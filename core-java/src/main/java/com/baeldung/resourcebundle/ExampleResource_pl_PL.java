package com.baeldung.resourcebundle;

import java.util.ListResourceBundle;

public class ExampleResource_pl_PL extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"keyA", "PL_pl_valueA"},
                {"keyD", new Double("44.44")}
        };
    }

}
