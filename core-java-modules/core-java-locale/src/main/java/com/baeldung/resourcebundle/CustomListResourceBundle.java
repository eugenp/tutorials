package com.baeldung.resourcebundle;

import java.util.ListResourceBundle;

public class CustomListResourceBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
            { "customMessage", "This is a custom message." }
        };
    }
}