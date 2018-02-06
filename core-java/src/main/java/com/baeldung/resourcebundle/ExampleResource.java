package com.baeldung.resourcebundle;

import java.util.ListResourceBundle;

public class ExampleResource extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"keyA", "valueA"},
                {"keyB", "valueB"},
                {"keyC", "valueC"}
        };
    }

}
